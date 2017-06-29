package hive;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorConverters;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.io.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 作者 : 王鹏
 * @createtime 创建时间：2016年9月19日 下午1:58:26 通过hive 查询 hbase 得到数据 结构式 数组
 * <p>
 * 并且里面是json格式的数据 hive本身不支持这种格式 所以需要本方法来识别
 * 首先把数据识别成hive认识的字符串数组在通过json_tuple方法来解析数组里的json数据
 */

public class UdfTest extends UDF {

    public String evaluate(String input) {
        return "aaaaaaaaaaaa";
    }
}
