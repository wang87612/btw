package spark

import org.apache.avro.generic.GenericRecord
import org.apache.avro.mapred.{AvroInputFormat, AvroWrapper}
import org.apache.hadoop.io.{LongWritable, NullWritable, Text}
import org.apache.hadoop.mapred.{FileSplit, InputSplit, TextInputFormat}
import org.apache.spark.rdd.HadoopRDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by btw on 2017/11/15.
  */
object LoadFileAndFileName extends App {

  val sparkConf = new SparkConf().setAppName("LoadFileAndFileName-")
    .setMaster("local[*]")
  val sc = new SparkContext(sparkConf)

  val fileRDD = sc.hadoopFile[AvroWrapper[GenericRecord], NullWritable, AvroInputFormat[GenericRecord]]("file:///Users/btw/Downloads/part-m-00003.avro")

  val hadoopRdd = fileRDD.asInstanceOf[HadoopRDD[AvroWrapper[GenericRecord], NullWritable]]

  val fileAndLine = hadoopRdd.mapPartitionsWithInputSplit((inputSplit: InputSplit, iterator: Iterator[(AvroWrapper[GenericRecord], NullWritable)]) => {
    val file = inputSplit.asInstanceOf[FileSplit]
    iterator.map(x => {
      file.getPath.getName + "\t" + x._1
    })
  })

  fileAndLine.foreach(println)


}
