/**
 * Created by Ashkan on 7/16/2015.
 */


import javax.transaction.*;
import com.arjuna.ats.arjuna.AtomicAction;
import org.jboss.logging.Logger;
import org.jboss.stm.Container;

public class App {

    private static final Logger logger = Logger.getLogger(App.class);


    public static void main(java.lang.String[] args) {
        App test = new App();

        Container<Counter> container = new Container<Counter>();

        Counter counterImpl1 = container.create(new CounterImpl());
        Counter counterImpl2 = container.create(new CounterImpl());
        Counter counterImpl3 = container.create(new CounterImpl());
        Counter counterImpl4 = container.create(new CounterImpl());
        Counter counterImpl5 = container.create(new CounterImpl());
        Counter counterImpl6 = container.create(new CounterImpl());
        Counter counterImpl7 = container.create(new CounterImpl());

        AtomicAction t1 = null;
        AtomicAction t2 = null;
        AtomicAction t3 = null;
        AtomicAction t4 = null;
        AtomicAction t5 = null;
        AtomicAction t6 = null;
        AtomicAction t7 = null;

        t1 = new AtomicAction();
        t1.begin();
        try {
            counterImpl1.increment();

            t2 = new AtomicAction();
            t2.begin();
            try {
                counterImpl2.increment();

                t5 = new AtomicAction();
                t5.begin();
                try {
                    counterImpl5.increment();
                }catch (Exception e){
                    abortRecursive(t5);
                }

                AtomicAction.resume(t2);

                t6 = new AtomicAction();
                t6.begin();
                try {
                    counterImpl6.increment();
                }catch (Exception e){
                    abortRecursive(t6);
                }

            }catch (Exception e){
                abortRecursive(t2);
            }

            AtomicAction.resume(t1);

            t3 = new AtomicAction();
            t3.begin();
            try {
                counterImpl3.increment();
            }catch (Exception e){
                abortRecursive(t3);
            }

            AtomicAction.resume(t1);

            t4 = new AtomicAction();
            t4.begin();
            try {
                counterImpl4.increment();

                t7 = new AtomicAction();
                t7.begin();
                try {
                    counterImpl7.increment();
                }catch (Exception e){
                    abortRecursive(t7);
                }

                //int b = 1 / 0;
                if (true)
                    throw new RuntimeException();

            }catch (Exception e){
                abortRecursive(t4);
            }

            commitRecursive(t1);

        }catch (Exception e){
            abortRecursive(t1);
        }


        System.out.println("Transaction T1 : \t" + t1 + "\t Counter : \t" + counterImpl1.get());
        System.out.println("Transaction T2 : \t" + t2 + "\t Counter : \t" + counterImpl2.get());
        System.out.println("Transaction T3 : \t" + t3 + "\t Counter : \t" + counterImpl3.get());
        System.out.println("Transaction T4 : \t" + t4 + "\t Counter : \t" + counterImpl4.get());
        System.out.println("Transaction T5 : \t" + t5 + "\t Counter : \t" + counterImpl5.get());
        System.out.println("Transaction T6 : \t" + t6 + "\t Counter : \t" + counterImpl6.get());
        System.out.println("Transaction T7 : \t" + t7 + "\t Counter : \t" + counterImpl7.get());

    }

    private static void rollback(UserTransaction userTransaction) {
        if (userTransaction != null) {
            try {
                userTransaction.rollback();
            } catch (IllegalStateException | SecurityException | SystemException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void abortRecursive(AtomicAction atomicAction){

        AtomicAction.resume(atomicAction);
        atomicAction.abort();

        if(atomicAction.childTransactions() != null){
            for (Object object : atomicAction.childTransactions()){
                abortRecursive((AtomicAction)object);
            }
        }
    }

    private static void commitRecursive(AtomicAction atomicAction){

        AtomicAction.resume(atomicAction);

        if (atomicAction.childTransactions() != null) {
            for (Object object : atomicAction.childTransactions()) {
                commitRecursive((AtomicAction) object);
            }
        }

        atomicAction.commit();
    }

}
