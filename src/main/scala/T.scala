import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by btw on 2018/8/7.
  */
object T extends App {


  val sparkConf = new SparkConf().setAppName("T-")
    .setMaster("local[*]")
  val sc = new SparkContext(sparkConf)


  val rdd1 = sc.textFile("/Users/btw/Downloads/query_result").repartition(10)
  val r = sc.textFile("/Users/btw/Downloads/allsql").collect()

  rdd1.map { v =>
    if (r.contains(v.split(",")(1)))
      (v.split(",")(1), 1)
    else
      (v.split(",")(1), 0)
  }.reduceByKey(_ + _).saveAsTextFile(System.currentTimeMillis() + "")

}
