import org.jboss.narayana.compensations.api.CompensationHandler;
import javax.transaction.Transactional;
import javax.inject.Inject;

/**
 * Created by Ashkan on 7/20/2015.
 */
public class EmailHandler implements CompensationHandler{

    @Inject
    Email email;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    //@Override
    public void compensate() {
        //Recall the package somehow
        System.out.println("Sorry " + email.getName() );
    }
}
