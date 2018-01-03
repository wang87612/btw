import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by btw on 2017/4/28.
  */
object ReadParquet extends App{

  val conf = new SparkConf().setAppName("ReadParquet").setMaster("local[*]")
  val sc = new SparkContext(conf)
  val hiveContext = new HiveContext(sc)
  val hcp1 = hiveContext.read.parquet("file:///Users/btw/Downloads/gaojiazheng")
  (hcp1.printSchema())
  (hcp1.show(100))

}











