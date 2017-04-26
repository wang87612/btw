import java.util.HashMap;
import java.util.Map;

/**
 * Created by btw on 2017/3/9.
 */
public abstract class CrawlerInputPreparer extends AbstractInputPreparer {

    @Override
    public Map<String, String> prepare() {
        Map<String, String> mh = new HashMap<String, String>();
        mh.put("1","2");
        return mh;
    }

    public static void main(String[] args) {

    }
}
