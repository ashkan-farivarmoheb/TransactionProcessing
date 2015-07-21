
import org.jboss.stm.annotations.ReadLock;
import org.jboss.stm.annotations.Transactional;
import org.jboss.stm.annotations.WriteLock;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Ashkan on 7/19/2015.
 */

@Transactional
public class CounterImpl implements Counter{

    private Integer counter;

    public CounterImpl() {
        counter = new Integer(0);
    }

//    @ReadLock
    public int get() {
        return counter;
    }

    public void decrement() { counter--;  }

    //@WriteLock
    public void increment() {
        counter++;
    }

}
