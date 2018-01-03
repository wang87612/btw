package http;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by btw on 2017/12/7.
 */
public class a {

    private HttpMethodBase createMethod(String url, int timeout) {
        PostMethod method = null;
        try {
            method = new PostMethod(url);
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("blog", "https://www.iteblog.com");
            jsonObject.put("Author", "iteblog");

            String transJson = jsonObject.toString();
            RequestEntity se = new StringRequestEntity(transJson, "application/json", "UTF-8");
            method.setRequestEntity(se);
            //使用系统提供的默认的恢复策略
            method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
            //设置超时的时间
            method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, timeout);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return method;
    }
}
