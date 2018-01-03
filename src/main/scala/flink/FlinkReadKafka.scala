package flink

import java.io.StringWriter
import java.util.Properties

import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.{CheckpointingMode, TimeCharacteristic}
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.fs.bucketing.BucketingSink
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010
import org.apache.flink.streaming.util.serialization.SimpleStringSchema

/**
  * Created by btw on 2017/3/7.
  */
object FlinkReadKafka {

  private val ZOOKEEPER_HOST = "base1.zookeeper.dc.puhuifinance.com:2181,base2.zookeeper.dc.puhuifinance.com:2181,base3.zookeeper.dc.puhuifinance.com:2181,base4.zookeeper.dc.puhuifinance.com:2181,base5.zookeeper.dc.puhuifinance.com:2181"
  private val KAFKA_BROKER = "192.168.176.94:6667,192.168.176.95:6667,192.168.176.96:6667,192.168.176.97:6667,192.168.176.98:6667"
  private val TRANSACTION_GROUP = "btw"

  def failHdfsSink(props: Properties): BucketingSink[String] = {
    val sink = new BucketingSink[String](props.getProperty("FAIL_HDFS_DIR"))

    //sink.setWriter(new StringWriter[String]())

    sink.setBatchSize(1024)
    //sink.setPendingPrefix("")
    sink
  }


  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.createLocalEnvironment()
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.enableCheckpointing(1000)
    env.getCheckpointConfig.setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE)

    // configure Kafka consumer
    val kafkaProps = new Properties()
    kafkaProps.setProperty("zookeeper.connect", ZOOKEEPER_HOST)
    kafkaProps.setProperty("bootstrap.servers", KAFKA_BROKER)
    kafkaProps.setProperty("group.id", TRANSACTION_GROUP)

    //topicd的名字是new，schema默认使用SimpleStringSchema()即可
    val transaction = env.addSource(new FlinkKafkaConsumer010("CifOplogOverallTopicDatapi", new SimpleStringSchema(), kafkaProps)).print()

    //    val windowCounts =  transaction.map(v => {
    //      (v, 1)
    //    })
    //
    //
    //    windowCounts.setParallelism(4).writeAsText("data")


    env.execute()


  }


}
