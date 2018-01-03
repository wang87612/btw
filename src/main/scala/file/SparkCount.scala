package file

import java.io.File

import org.apache.spark.{SparkConf, SparkContext}

import scala.io.Source

/**
  * Created by btw on 2017/12/14.
  */
object SparkCount extends App {

      val sparkConf = new SparkConf().setAppName("CheckMiss").setMaster("local")
      val sc = new SparkContext(sparkConf)

      sc.textFile("file:///Users/btw/Downloads/delete1218.txt").filter(_.contains("lend_request_upload") ).repartition(1).saveAsTextFile("file:///Users/btw/result3/")



}
