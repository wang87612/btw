package avro

import org.apache.avro.generic.GenericRecord
import org.apache.avro.mapred.{AvroInputFormat, AvroWrapper}
import org.apache.hadoop.io.NullWritable
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by btw on 2017/4/21.
  */
object SparkReadAvro extends App {

  //3,create sc
  val sparkConf = new SparkConf().setAppName("SparkReadAvro")
    .setMaster("local[*]")
  val sc = new SparkContext(sparkConf)

  val path = "file:///Users/btw/Downloads/part-m-00000.avro"
  sc.hadoopFile[AvroWrapper[GenericRecord], NullWritable, AvroInputFormat[GenericRecord]](path).foreach(v => {
    if (v._1.datum().get("id").toString.equals("566367")) {
      println(v._1.datum().get("complete_address"))
    }
  })
}
