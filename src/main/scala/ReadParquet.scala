import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by btw on 2017/4/28.
  */
object ReadParquet extends App{

  val conf = new SparkConf().setAppName("SparkWriteParquet").setMaster("local[*]")
  val sc = new SparkContext(conf)
  val hiveContext = new HiveContext(sc)

  hiveContext.read.parquet("file:///Users/btw/Downloads/part-r-00000-4bd8b17a-46e0-4e11-a180-a8e99971f557.gz.parquet").printSchema()

}
