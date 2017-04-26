import java.util.Map;

/**
 * Created by btw on 2017/3/9.
 */
public class AlpInputPreparer extends CrawlerInputPreparer {

    @Override
    public Map<String, String> prepare() {

        System.out.println(super.prepare());
        return null;
    }


    public static void main(String[] args) {
        new AlpInputPreparer().prepare();
    }
}
