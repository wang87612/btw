import java.io.{File, PrintWriter}
import java.sql.{DriverManager, ResultSet}

import org.apache.hadoop.hive.ql.parse.ParseDriver

import scala.collection.mutable.ArrayBuffer
import scala.collection.JavaConversions._

/**
  * Created by wangpeng on 2016/12/2.
  */
object MysqlJdbc extends App {

  val dbc = "jdbc:mysql://192.168.158.100:3307/cif_offline_check?user=prod_hanxiaoqiang&password=prod_hanxiaoqiang.jKcKnsDBDoNCymr5xvnrJqf4GMqmBs4T"


  val connection = (sql: String, resultLogic: (ResultSet) => Any) => {

    val conn = DriverManager.getConnection(dbc)
    try {
      classOf[com.mysql.jdbc.Driver]
      val statement = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)
      resultLogic(statement.executeQuery(sql))
    } finally {
      conn.close()
    }
  }
  val writer = new PrintWriter(new File("/Users/btw/sql.txt"))
  connection("select stament from cif_offline_check.spark_monitor where start_time>='2018/06/01'", result => {
    while (result.next()) {
      try {
        // val pd = new ParseDriver()
        //        ParserTableName.getTableNameBySql(result.getString(1)).foreach(println(_))
        //val ast = pd.parse(result.getString(1))
        //val strTree = ast.toStringTree()
        //System.out.println(strTree)

        writer.write(result.getString(1).replaceAll("\n", " ") + System.getProperty("line.separator"))

      } catch {
        case e: Exception => null
      }

    }

    writer.flush()
    writer.close()
  })
}