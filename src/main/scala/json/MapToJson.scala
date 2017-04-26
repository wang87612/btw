package json

import org.json4s.DefaultFormats
import org.json4s.jackson.Json

/**
  * Created by btw on 2017/2/27.
  */
object MapToJson extends App {

  val hm = Map("data" -> "", "logfileOffset" -> -1, "sourceType" -> "MYSQL", "serverenCode" -> "UTF-8"
    , "dbName" -> "", "logfileName" -> "mysql-bin.000327", "eventType" -> "INSERT", "tableName" -> ""
    , "executeTime" -> 1487821626000l)
  println(Json(DefaultFormats).write(hm).toString)
}
