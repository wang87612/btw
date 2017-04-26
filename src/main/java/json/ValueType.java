package json;


import com.alibaba.fastjson.JSONObject;

import java.util.Iterator;

/**
 * Created by btw on 2017/4/26.
 */
public class ValueType {
    public static void main(String[] args) {


        JSONObject object = JSONObject.parseObject("{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"A green door\",\n" +
                "  \"price\": 12.555,\n" +
                "  \"checked\": false,\n" +
                "  \"tags\": [\n" +
                "    \"home\",\n" +
                "    \"green\"\n" +
                "  ]\n" +
                "}");


        System.out.println(object.get("price").getClass().getSimpleName());


    }
}
