import javax.inject.Inject;

/**
 * Created by Ashkan on 7/20/2015.
 */
public class EmailServiceTest {

    EmailService emailService = new EmailService();

    public static void main(java.lang.String[] args){
        EmailServiceTest test = new EmailServiceTest();

        test.emailService.sendEmail("Ashkan");
    }

}
