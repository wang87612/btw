import org.apache.spark.rdd.RDD
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}
import util.control.Breaks._

/**
  * Created by btw on 2017/12/25.
  */
object IdPidmatch extends App {

  val m = Array(
    ("1", "2"),
    ("2", "3"),
    ("3", "4"),
    ("4", "null"),
    ("5", "2"),
    ("7", "3")
  )

  val sparkConf = new SparkConf().setAppName("IdPidmatch-")
    .setMaster("local[*]")
  val sc = new SparkContext(sparkConf)
  val hiveContext = new HiveContext(sc)
  hiveContext.createDataFrame(sc.parallelize(m).filter(_._2 == "null")).registerTempTable("k1")
  hiveContext.createDataFrame(sc.parallelize(m).filter(_._2 != "null")).registerTempTable("v1")
  hiveContext.sql("select k1.`_1`,v1.`_1` as k2_1 from k1 left join v1 on  k1.`_1` = v1.`_2`").registerTempTable("k2")

  breakable {
    for (i <- 2 to 1000) {
      val tmp = hiveContext.sql("select k" + i + ".*,v1.`_1` as k" + (i + 1) + "_1 from k" + i + " left join  v1  on  k" + i + ".k" + i + "_1 = v1.`_2`")
      if (tmp.where("k" + (i + 1) + "_1 != 'null'").rdd.count() == 0) {
        tmp.registerTempTable("final_result")
        break()
      }
      else
        tmp.registerTempTable("k" + (i + 1))
    }
  }

  hiveContext.sql("select * from final_result").show(10000)
}
