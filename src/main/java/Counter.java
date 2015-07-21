import org.jboss.stm.annotations.Transactional;

/**
 * Created by Ashkan on 7/20/2015.
 */

@Transactional
public interface Counter {
    public int get();
    public void increment();
    public void decrement();
}
