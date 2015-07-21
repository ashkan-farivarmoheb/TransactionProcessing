import com.arjuna.ats.arjuna.AtomicAction;
import com.arjuna.ats.arjuna.coordinator.ActionStatus;
import org.jboss.logging.Logger;
import org.jboss.stm.Container;

/**
 * Created by Ashkan on 7/20/2015.
 */
public class ChainWithCompensate {

    private static final Logger logger = Logger.getLogger(ChainWithCompensate.class);


    public static void main(java.lang.String[] args) {
        ChainWithCompensate test = new ChainWithCompensate();

        Container<Counter> container = new Container<Counter>();
        Counter counterImpl1 = container.create(new CounterImpl());

        AtomicAction t1 = new AtomicAction();
        AtomicAction t2 = new AtomicAction();

        t1.begin();
        System.out.println("t1 begun");
        try {
            counterImpl1.increment();
            t1.commit();
            System.out.println("t1 committed");
        } catch (Exception e) {
            t1.abort();
        }

        if (t1.status() == ActionStatus.COMMITTED) {
            t2.begin();
            System.out.println("t2 begun");
            try {
                counterImpl1.increment();
                if (true)
                    throw new Exception("t2 Not committing");
                t2.commit();
            } catch (Exception e) {
                t2.abort();
                System.out.println(e.getMessage());
                System.out.println("Compensate t1");
                counterImpl1.decrement();
            }
        }

        System.out.println("Counter : " + counterImpl1.get());
    }
}
