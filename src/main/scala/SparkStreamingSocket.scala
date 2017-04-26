import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by wangpeng on 2016/11/29.
  */
object SparkStreamingSocket {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("SparkDemo4").setMaster("local")
    val ssc = new StreamingContext(conf, Seconds(1))
    val lines = ssc.socketTextStream("172.16.4.31", 9999)
    val words = lines.flatMap(_.split(" "))
    val pairs = words.map(word => (word, 1))
    val wordCounts = pairs.reduceByKey(_ + _)
    wordCounts.print()
    ssc.start()
    ssc.awaitTermination()
  }
}
