package mysql

import java.io.{File, PrintWriter}
import java.sql.{DriverManager, ResultSet}

/**
  * Created by btw on 2017/4/21.
  */
object MakeMysqlSchema extends App {

  val conn_str = "jdbc:mysql://192.168.153.3:3306/puhui"
  // Load the driver
  classOf[com.mysql.jdbc.Driver]
  // Setup the connection
  val conn = DriverManager.getConnection(conn_str, "cif", "yoEEHYnAHL^X!!B0")

  try {
    // Configure to be Read Only
    val statement = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)
    val statement2 = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)

    // Execute Query
    val rs = statement.executeQuery("show tables")
    // Iterate Over ResultSet
    while (rs.next) {

      val tmp = statement2.executeQuery("show create table " + rs.getString(1))

      if (tmp.next){
        val writer = new PrintWriter(new File("/Users/btw/puhui-schema/"+rs.getString(1)+"-schema.sql"))
        writer.write(tmp.getString(2))
        writer.close()
      }

      //println("id = "+rs.getInt("id"))
      //println("city = "+rs.getString("city"))
      //println("complete_address = "+rs.getString("complete_address").replaceAll("\r", ""))
      //println("dist = "+rs.getString("dist"))
      //println("housenumber = "+rs.getString("housenumber"))
    }
  } catch {
    case e: Exception => e.printStackTrace
  }
  finally {
    conn.close
  }

}
