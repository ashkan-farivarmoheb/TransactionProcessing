import org.jboss.narayana.compensations.api.CompensationScoped;

import java.io.Serializable;

/**
 * Created by Ashkan on 7/20/2015.
 */

@CompensationScoped
public class Email implements Serializable{
    private String name;

    public String getName() {
        return name;
    }
}
