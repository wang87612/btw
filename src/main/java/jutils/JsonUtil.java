package jutils;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * json工具类
 * @author kainal
 */
public class JsonUtil {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    private static ObjectMapper objectMapper = new ObjectMapper();
    private JsonUtil() {
    }

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }
    /**
     * 将Java对象转为json字符串
     *
     * @author xiaobowen
     * @param obj
     * @return
     * @throws Exception
     */
    public static String objectToJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            logger.error("objectToJson error:{}"+ e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 将Java对象转为json字符串
     *
     * @author xiaobowen
     * @param obj
     * @return
     * @throws Exception
     */
    public static String objectToJson(Object obj, DateFormat df) {
        try {
            ObjectMapper om = new ObjectMapper();
            om.setDateFormat(df);
            return om.writeValueAsString(obj);
        } catch (Exception e) {
            logger.error("objectToJson error:{}"+ e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 将Java对象转为json字符串
     *
     * @author xiaobowen
     * @param obj
     * @return
     * @throws Exception
     */
    public static String objectToJson(Object obj, DateFormat df, JsonSerializer<Object> nvs) {
        try {
            ObjectMapper om = new ObjectMapper();
            om.setDateFormat(df);
            om.getSerializerProvider().setNullValueSerializer(nvs);
            return om.writeValueAsString(obj);
        } catch (Exception e) {
            logger.error("objectToJson error:{}"+ e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 将json字符串转为Java对象
     *
     * @author xiaobowen
     * @param json
     * @param clazz
     * @return
     * @throws Exception
     */
    public static <T> T jsonToObject(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 将json字符串转为Java对象
     *
     * @author xiaobowen
     * @param json
     * @return
     * @throws Exception
     */
    public static <T> T jsonToObject(String json, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (Exception e) {
            logger.error("jsonToObject error:{}"+ e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 将json字符串转为Java对象
     *
     * @author xiaobowen
     * @param json
     * @return
     * @throws Exception
     */
    public static <T> T jsonToObject(String json, TypeReference<T> typeReference, DateFormat df) {
        try {
            ObjectMapper om = new ObjectMapper();
            om.setDateFormat(df);
            return om.readValue(json, typeReference);
        } catch (Exception e) {
            logger.error("jsonToObject error:{}"+ e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @json解析是否正确
     * @param result
     * @return
     * @author wangmeng
     * @time 2015年5月4日下午9:58:49
     */
    public static boolean parseObjectTrue(String result) {
        try {
            JSON.parseObject(result);
            return true;
        } catch (Exception e) {
            logger.error("parseObjectTrue error:{}"+ e);
            return false;
        }
    }

    public static boolean parseArrayTrue(String result) {
        try {
            JSON.parseArray(result);
            return true;
        } catch (Exception e) {
            logger.error("parseArrayTrue error:{}"+ e);
            return false;
        }
    }

}
