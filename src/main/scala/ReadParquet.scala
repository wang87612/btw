import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by btw on 2017/4/28.
  */
object ReadParquet extends App {

  val conf = new SparkConf().setAppName("ReadParquet").setMaster("local[*]")
  val sc = new SparkContext(conf)
  val hiveContext = new HiveContext(sc)
  val hcp1 = hiveContext.read.parquet("file:///Users/btw/Downloads/1533281522819-11939376-part-r-00012-1e3f262e-c4a7-4b5c-a30a-6e383c49b4f1.gz.parquet").show()





}











