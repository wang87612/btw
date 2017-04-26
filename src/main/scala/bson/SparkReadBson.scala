package bson


import com.mongodb.hadoop.BSONFileInputFormat
import org.apache.hadoop.conf.Configuration

import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.bson.BSONObject


/**
  * Created by btw on 2017/2/7.
  *
  * Spark读取Bson文件
  * 执行程序后会在Bson文件同目录下生成一个split文件
  * 如果第二次在操作会找到这个文件 就会比第一次快很多因为spark已经知道从哪里到哪里取加载一个partition的数据
  */
object SparkReadBson {

  def main(args: Array[String]): Unit = {
    val in = "file://" + System.getProperty("user.dir") + "/src/main/resources/bson/AlipayStore.bson"

    val conf = new SparkConf().setAppName("SparkReadBson").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val mongoConfig = new Configuration()

    mongoConfig.set("mongo.job.input.format",
      "com.mongodb.hadoop.BSONFileInputFormat")
    mongoConfig.set("mapred.input.dir", in)

    val rdd: RDD[(String, BSONObject)] = sc.newAPIHadoopRDD(mongoConfig,
      classOf[BSONFileInputFormat].asSubclass(classOf[FileInputFormat[String,
        BSONObject]]), classOf[String], classOf[BSONObject])

    rdd.foreach(s => {
      println(s)
    })
  }
}
