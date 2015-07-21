import org.jboss.narayana.compensations.api.Compensatable;
import org.jboss.narayana.compensations.api.CompensationTransactionType;
import org.jboss.narayana.compensations.api.TxCompensate;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;

/**
 * Created by Ashkan on 7/19/2015.
 */
public class EmailService {

    @Compensatable(CompensationTransactionType.MANDATORY)
    @TxCompensate(EmailHandler.class)
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void sendEmail(String name){
        //normalEmail.send(name);
        System.out.println("Send Email to : " + name);
        int a = 1 / 0;
    }

}
