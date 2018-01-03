

import org.slf4j.LoggerFactory;
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by wangpeng on 2016/11/29.
  */
object Spark {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("Spark").setMaster("local")
    val sc = new SparkContext(conf)
    val data = sc.makeRDD(1 to 10)
     data.map(n => {
      (1, n)
    }).groupByKey().collect().foreach(println)




  }
}
