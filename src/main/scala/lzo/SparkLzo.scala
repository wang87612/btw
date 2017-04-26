import com.hadoop.compression.lzo.LzopCodec
import com.hadoop.mapred.DeprecatedLzoTextInputFormat
import com.hadoop.mapreduce.LzoTextInputFormat
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.io.{LongWritable, Text}
import org.apache.hadoop.mapred.{JobConf, TextOutputFormat}
import org.apache.hadoop.mapreduce.Job
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.hadoop.mapreduce.JobContext

/**
  * Created by btw on 2016/12/19.
  */
object SparkLzo extends App {
  val sparkConf = new SparkConf().setAppName("SparkLzo").setMaster("local[*]")
  val sc = new SparkContext(sparkConf)
  val fileName = "hdfs://127.0.0.1:8020/data/cloud/flume/test/cif_xuzh@ciftest@2017-01-04T172819.1483522100454.lzo"
  sc.hadoopConfiguration.set("io.compression.codecs", "org.apache.hadoop.io.compress.GzipCodec,org.apache.hadoop.io.compress.DefaultCodec,org.apache.hadoop.io.compress.BZip2Codec,org.apache.hadoop.io.compress.SnappyCodec,com.hadoop.compression.lzo.LzoCodec,com.hadoop.compression.lzo.LzopCodec")

//  val ctx = new Job(sc.hadoopConfiguration);
//
//  val job = new Job()
//  job.getConfiguration().set("mapred.output.compress", "true")
//  job.getConfiguration().set("mapred.output.compression.codec", "com.hadoop.compression.lzo.LzopCodec")
//
//  //val lines = sc.newAPIHadoopFile[LongWritable, Text, LzoTextInputFormat](fileName).collect()
//  val textFile1 = sc.newAPIHadoopFile(fileName, classOf[LzoTextInputFormat],classOf[LongWritable], classOf[Text],job.getConfiguration())
//  textFile1.collect().foreach(println)
//  //val textFile2 = sc.hadoopFile(fileName, classOf[DeprecatedLzoTextInputFormat],classOf[LongWritable], classOf[Text], 1)
//  //val textFile3 = sc.textFile(fileName)
  sc.textFile(fileName).collect()
  //sc.hadoopFile(fileName, classOf[DeprecatedLzoTextInputFormat], classOf[LongWritable], classOf[Text], 1).foreach(kv => println(kv._2))


}
