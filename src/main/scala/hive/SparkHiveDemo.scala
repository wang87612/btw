package hive

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.hive.HiveContext

/**
  * Created by btw on 2017/2/14.
  */
object SparkHiveDemo extends App{
  val sparkConf = new SparkConf().setAppName("Parquet").setMaster("local[*]")
  val sc = new SparkContext(sparkConf)
  val hiveContext = new HiveContext(sc)

  hiveContext.sql("SHOW DATABASES").show()

}
