import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}


/**
  * Created by wangpeng on 2016/11/29.
  */
object SparkStreamingKafka extends App {
  val zkQuorum = "localhost:2181"
  val group = "wangpeng"
  val topics = "TrickletCifUtcs"
  val numThreads = "1"
  val conf = new SparkConf().setAppName("SparkStreamingKafka").setMaster("local[*]")
  //conf.set("spark.streaming.receiver.writeAheadLog.enable", "true")
  val ssc = new StreamingContext(conf, Seconds(5))
  //ssc.checkpoint("checkpoint")

  val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap
  val lines = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap, StorageLevel.MEMORY_AND_DISK_SER).map(_._2)

  // /cif/basic_data/mongo/db/collection/dt=2016-10-10/
  // /cif/basic_data/mysql/db/table/dt=2016-10-10/

  lines.print()

  //sys.addShutdownHook(ssc.stop(true, true))


  ssc.start()
  ssc.awaitTermination()
}
