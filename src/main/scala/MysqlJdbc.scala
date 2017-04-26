import java.sql.{DriverManager, ResultSet}

import scala.collection.mutable.ArrayBuffer

/**
  * Created by wangpeng on 2016/12/2.
  */
object MysqlJdbc extends App {

  val dbc = "jdbc:mysql://172.16.4.31:3306/hive?user=root&password=123456"


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

  println(connection("SELECT * FROM CDS", result => {
    val arrbuf = ArrayBuffer[Any]()
    while (result.next()) {
      arrbuf += (result.getString(1))
    }
    arrbuf
  }))
}