package flink

import java.util.Properties

import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.{CheckpointingMode, TimeCharacteristic}
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010
import org.apache.flink.streaming.util.serialization.SimpleStringSchema

/**
  * Created by btw on 2017/3/7.
  */
object FlinkReadKafka extends App {

  private val ZOOKEEPER_HOST = "localhost:2181"
  private val KAFKA_BROKER = "localhost:9092"
  private val TRANSACTION_GROUP = "btw"

  val env = StreamExecutionEnvironment.createLocalEnvironment()
  env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
  env.enableCheckpointing(5000)
  env.getCheckpointConfig.setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE)

  // configure Kafka consumer
  val kafkaProps = new Properties()
  kafkaProps.setProperty("zookeeper.connect", ZOOKEEPER_HOST)
  kafkaProps.setProperty("bootstrap.servers", KAFKA_BROKER)
  kafkaProps.setProperty("group.id", TRANSACTION_GROUP)

  //topicd的名字是new，schema默认使用SimpleStringSchema()即可
  val transaction = env.addSource(new FlinkKafkaConsumer010("TrickletTrickleTest", new SimpleStringSchema(), kafkaProps))

  transaction.map(v => {
    (v, 1)
  })

  //transaction.setParallelism(4).writeAsText("data")


  env.execute()


}
