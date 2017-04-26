import java.util.HashMap;
import java.util.Map;

/**
 * Created by btw on 2017/3/9.
 */
public abstract class AbstractInputPreparer implements InputPreparer {

    protected Map<String, String> request() {
        Map<String, String> mh = new HashMap<String, String>();
        return mh;
    }
}
