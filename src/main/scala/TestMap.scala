import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by btw on 2018/3/23.
  */
object TestMap extends App {

  val sparkConf = new SparkConf().setAppName("TestMap")
  val sc = new SparkContext(sparkConf)
  val s = Array("1", "2", "3", "4", "5")
  val rdd = sc.parallelize(s)

  val colors = scala.collection.mutable.Map("red" -> "#FF0000", "azure" -> "#F0FFFF")

  rdd.map { c =>
    colors.put(c, "2")
  }.collect()

  println(colors)


}
