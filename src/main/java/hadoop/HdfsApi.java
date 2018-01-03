package hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

import java.net.URI;

/**
 * Created by btw on 2017/11/7.
 */
public class HdfsApi {

    public static void main(String[] args) throws Exception
    {
        String dest = "hdfs://192.168.176.62:8020/user/hive/warehouse/cif_finup_lend.db/system_regions";
        String local = "/Users/john_liu/Finupprojects/adpHiveUDF/src/main/resources/temp";
        Configuration conf = new Configuration();
        //conf.set("fs.hdfs.impl",org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        FileSystem fs = FileSystem.get(new URI("hdfs://192.168.176.62:8020"),conf);
        //FSDataInputStream fsdi = fs.open(new Path(dest));
        //OutputStream output = new FileOutputStream(local);
        //IOUtils.copyBytes(fsdi,output,4096,true);
    }
}
