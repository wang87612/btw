//package flink
//
//import java.io.File
//import java.util.Properties
//
//import com.alibaba.fastjson.JSONObject
//import com.google.common.collect.Lists
//import com.typesafe.config.ConfigFactory
//import org.apache.flink.streaming.api.{CheckpointingMode, TimeCharacteristic}
//import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
//import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010
//import org.apache.flink.streaming.util.serialization.SimpleStringSchema
//import org.joda.time.DateTime
//
///**
//  * Created by btw on 2017/6/19.
//  */
//object Binlog {
//
//  //预留的10个字段
//  private def reserveTenColumn(name: String, value: String, `type`: String) = {
//    val sortTimeObject = new JSONObject
//    sortTimeObject.put("name", name)
//    sortTimeObject.put("value", value)
//    sortTimeObject.put("type", `type`)
//    sortTimeObject
//  }
//
//  //设置主要排序字段
//  private def timeCol(time: Long) = {
//    val sortTimeObject = new JSONObject
//    sortTimeObject.put("name", "cif_tr_sortTime")
//    sortTimeObject.put("value", time)
//    sortTimeObject.put("type", "long")
//    sortTimeObject
//  }
//
//  //设置操作类型
//  private def optionType(operationType: String) = {
//    val sortTimeObject = new JSONObject
//    sortTimeObject.put("name", "cif_tr_optionFlag")
//    sortTimeObject.put("value", operationType)
//    sortTimeObject.put("type", "string")
//    sortTimeObject
//  }
//
//  //日期处理
//  def dayOfHour(dtime: Long): String = {
//    val dt = new DateTime(dtime)
//    val year = dt.getYear
//    val mon = dt.getMonthOfYear
//    val month = if (mon <= 9) "0" + mon
//    else mon + ""
//    val d = dt.getDayOfMonth
//    val day = if (d <= 9) "0" + d
//    else d + ""
//    val h = dt.getHourOfDay
//    val hour = if (h <= 9) "0" + h
//    else h + ""
//    year + month + day + hour
//  }
//
//  private def insertAndUpdate(time: Long, `type`: String) = {
//    val jsonList = Lists.newArrayList
//    jsonList.add(timeCol(time))
//    jsonList.add(optionType(`type`))
//    jsonList.add(reserveTenColumn("cif_tr_partitionDate", dayOfHour(time), "string"))
//    jsonList.add(reserveTenColumn("cif_tr_3", "", "string"))
//    jsonList.add(reserveTenColumn("cif_tr_4", "", "string"))
//    jsonList.add(reserveTenColumn("cif_tr_5", "", "string"))
//    jsonList.add(reserveTenColumn("cif_tr_6", "", "string"))
//    jsonList.add(reserveTenColumn("cif_tr_7", "", "string"))
//    jsonList.add(reserveTenColumn("cif_tr_8", "", "string"))
//    jsonList.add(reserveTenColumn("cif_tr_9", "", "string"))
//    import scala.collection.JavaConversions._
//    for (column <- rowData.getAfterColumnsList) {
//      val columnObject = new JSONObject
//      columnObject.put("name", column.getName)
//      columnObject.put("value", column.getValue)
//      columnObject.put("type", column.getMysqlType)
//      jsonList.add(columnObject)
//    }
//    jsonList
//  }
//
//  def main(args: Array[String]): Unit = {
//
//    val conf = ConfigFactory.load(ConfigFactory.parseFile(new File(args(0))))
//
//    val env = StreamExecutionEnvironment.createLocalEnvironment()
//    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
//    env.enableCheckpointing(5000)
//    env.getCheckpointConfig.setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE)
//
//    // configure Kafka consumer
//    val kafkaProps = new Properties()
//    kafkaProps.setProperty("zookeeper.connect", conf.getString("ZOOKEEPER_HOST"))
//    kafkaProps.setProperty("bootstrap.servers", conf.getString("KAFKA_BROKER"))
//    kafkaProps.setProperty("group.id", conf.getString("TRANSACTION_GROUP"))
//
//    //topicd的名字是new，schema默认使用SimpleStringSchema()即可
//    val transaction = env.addSource(new FlinkKafkaConsumer010("TrickletTrickleTest", new SimpleStringSchema(), kafkaProps))
//
//    transaction.map(v => {
//      (v, 1)
//    })
//
//
//  }
//
//
//}
