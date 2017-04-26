import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by btw on 2017/2/28.
  */
object GroupByKeyTest {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("GroupByKeyTest")
    val sc = new SparkContext(sparkConf)
    sc.textFile(args(0)).map(v => {
      ("1", v)
    }).groupByKey().map(i => {
      i._2.map(v => {

      })
    }).collect()
  }

}
