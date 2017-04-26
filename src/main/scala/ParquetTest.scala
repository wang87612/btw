import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.Row
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.sql.types.{DataTypes, StructField, StructType}

/**
  * Created by wangpeng on 2016/12/9.
  */
object ParquetTest extends App {
  val conf = new SparkConf().setAppName("ParquetTest").setMaster("local")
  val sc = new SparkContext(conf)
  val hiveContext = new HiveContext(sc)

  val rddRow = sc.parallelize(Array(Row("4v.parquet", "wan123gpeng", "24", "beijing")))
  val schema = StructType(Array(StructField("0", DataTypes.StringType),
    StructField("1", DataTypes.StringType), StructField("2", DataTypes.StringType), StructField("3", DataTypes.StringType)))

  val idAgeDF = hiveContext.createDataFrame(rddRow, schema)
  idAgeDF.printSchema()
  idAgeDF.write.parquet("./outParquet")


}
