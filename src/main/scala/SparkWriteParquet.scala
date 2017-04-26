
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.sql.{Row, SQLContext}
import org.apache.spark.sql.types.{DataTypes, StringType, StructField, StructType}
import org.apache.spark.{SparkConf, SparkContext}


/**
  * Created by wangpeng on 2016/11/29.
  *
  * Spark读写Parquet文件
  */
object SparkWriteParquet {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("SparkWriteParquet")
    val sc = new SparkContext(conf)
    val hiveContext = new HiveContext(sc)

    val idAgeRDDRow = sc.parallelize(Array(Row("3", "2016-01-01",
      Array(Row("1a1", "1b1", "1c1", "1d1", "1e1"), Row("1a2", "1b2", "1c2", "1d2", "1e2")),
      Array(Row("2a1", "2b1", "2c1", "2d1", "2e1"), Row("2a2", "2b2", "2c2", "2d2", "2e2"), Row("2a3", "2b3", "2c3", "2d3", "2e3")), "jinrixinxi", "xxxxxxxxxx")))

    val schema = StructType(Array(StructField("id", DataTypes.StringType), StructField("ct", DataTypes.StringType),

      StructField("info_1", DataTypes.createArrayType(
        DataTypes.createStructType(Array(
          StructField("info_1_a", DataTypes.StringType),
          StructField("info_1_b", DataTypes.StringType),
          StructField("info_1_c", DataTypes.StringType),
          StructField("info_1_d", DataTypes.StringType),
          StructField("info_1_e", DataTypes.StringType)))))
      ,
      StructField("info_2", DataTypes.createArrayType(
        DataTypes.createStructType(Array(
          StructField("info_2_a", DataTypes.StringType),
          StructField("info_2_b", DataTypes.StringType),
          StructField("info_2_c", DataTypes.StringType),
          StructField("info_2_d", DataTypes.StringType),
          StructField("info_2_e", DataTypes.StringType)))))
      ,
      StructField("title", DataTypes.StringType)
      ,
      StructField("address", DataTypes.StringType)
    ))

    val idAgeDF = hiveContext.createDataFrame(idAgeRDDRow, schema)
    idAgeDF.printSchema()
    idAgeDF.show()
    println(idAgeDF.count())

    idAgeDF.map(s => s).groupBy(s => s).collect()

    //idAgeDF.write.parquet("./" + System.currentTimeMillis)
    //
    //  val par = hiveContext.read.parquet("./1480505449399/part-r-00000-94c95003-bbfb-4634-9401-1b1a5f64f84e.gz.parquet")
    //  par.show(10)

    //hiveContext.sql("CREATE TABLE IF NOT EXISTS parquet_table(id INT, age STRING) STORED AS PARQUET")
    //hiveContext.sql(" alter table parquet_table add COLUMNS (name string)")

    //idAgeDF.write.insertInto("test")
    //hiveContext.sql("SELECT * FROM").show(100)

    //hiveContext.sql(args(0)).show(10000)

    sc.stop()
  }

}
