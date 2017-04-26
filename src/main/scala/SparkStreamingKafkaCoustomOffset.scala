import org.apache.log4j._
import kafka.common.TopicAndPartition
import kafka.message.MessageAndMetadata
import kafka.utils.{ZKGroupTopicDirs, ZkUtils}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.{HasOffsetRanges, KafkaUtils, OffsetRange}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import kafka.serializer.StringDecoder
import org.I0Itec.zkclient.ZkClient
import org.apache.spark.streaming.dstream.InputDStream

/**
  * Created by wangpeng on 2016/11/29.
  *
  * 自定义kafka游标
  */
object SparkStreamingKafkaCoustomOffset{

  private val logger = Logger.getLogger(getClass().getName())

  //获取offset并创建数据流
  def getStream(topic: String, zkClient: ZkClient, topicDirs: ZKGroupTopicDirs, ssc: StreamingContext, kafkaParams: Map[String, String]): InputDStream[(String, String)] = {
    var messages: InputDStream[(String, String)] = null
    //查询该路径下是否字节点（默认有字节点为我们自己保存不同 partition 时生成的）
    val children = zkClient.countChildren(topicDirs.consumerOffsetDir)
    //如果 zookeeper 中有保存 offset，我们会利用这个 offset 作为 kafkaStream 的起始位置
    var fromOffsets: Map[TopicAndPartition, Long] = Map()
    if (children > 0) {
      //如果 zookeeper 里有记录则获取
      for (i <- 0 until children) {
        val partitionOffset = zkClient.readData[String](s"${topicDirs.consumerOffsetDir}/${i}")
        val tp = TopicAndPartition(topic, i)
        fromOffsets += (tp -> partitionOffset.toLong) //将不同 partition 对应的 offset 增加到 fromOffsets 中
        logger.info(s"@@@@@@ topic[${topic}] partition[${i}] offset[${partitionOffset}] @@@@@@")
      }
      //这个会将 kafka 的消息进行 transform，最终 kafak 的数据都会变成 (topic_name, message) 这样的 tuple
      val messageHandler = (mmd: MessageAndMetadata[String, String]) => (mmd.topic, mmd.message())
      messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder, (String, String)](ssc, kafkaParams, fromOffsets, messageHandler)
    } else {
      //如果未保存，根据 kafkaParam 的配置使用最新或者最旧的 offset
      messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, Set(topic))
    }
    messages
  }

  //处理完数据保存offset
  def saveOffset(messages: InputDStream[(String, String)], zkClient: ZkClient, topicDirs: ZKGroupTopicDirs): Unit = {
    //开始保存offset
    var offsetRanges = Array[OffsetRange]()
    messages.foreachRDD { rdd =>
      offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
      offsetRanges.foreach(o => {
        val zkPath = s"${topicDirs.consumerOffsetDir}/${o.partition}"
        //将该 partition 的 offset 保存到 zookeeper
        ZkUtils.updatePersistentPath(zkClient, zkPath, o.fromOffset.toString)
        logger.info(s"@@@@@@ topic  ${o.topic}  partition ${o.partition}  fromoffset ${o.fromOffset}  untiloffset ${o.untilOffset} #######")
      })
    }
  }

  def main(args: Array[String]): Unit = {
    val topic = "wangpeng_test1"

    //初始化spark环境
    val conf = new SparkConf().setAppName("SparkDemo6").setMaster("local[*]")
    val ssc = new StreamingContext(conf, Seconds(5))
    val kafkaParams = Map[String, String]("metadata.broker.list" -> "da1.kafka.dc.puhuifinance.com:6667,da2.kafka.dc.puhuifinance.com:6667,da3.kafka.dc.puhuifinance.com:6667")
    var messages: InputDStream[(String, String)] = null

    //创建一个 ZKGroupTopicDirs 对象，保存
    val topicDirs = new ZKGroupTopicDirs("wangpeng_test_spark_streaming_group", topic)
    //获取 zookeeper 中的路径，这里会变成 /consumers/wangpeng_test_spark_streaming_group/offsets/topic_name
    val zkTopicPath = s"${topicDirs.consumerOffsetDir}"
    val zkClient = new ZkClient("da1.zookeeper.dc.puhuifinance.com:40000,da2.zookeeper.dc.puhuifinance.com:40000,da3.zookeeper.dc.puhuifinance.com:40000")

    messages = getStream(topic, zkClient, topicDirs, ssc, kafkaParams)
    messages.map(_._2).print()

    saveOffset(messages, zkClient, topicDirs)

    sys.addShutdownHook(ssc.stop(true, true))
    ssc.start()
    ssc.awaitTermination()

  }


}
