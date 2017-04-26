import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by wangpeng on 2016/11/29.
  */
object Spark{
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("SparkDemo3").setMaster("local")
    val sc = new SparkContext(conf)
    val data = sc.makeRDD(1 to 10)
    val rdd = data.map(n => {
      (1,n)
    }).groupByKey().collect().foreach(println)

    print(sc.textFile("file:///Users/btw/test.txt").count())




  }
}
