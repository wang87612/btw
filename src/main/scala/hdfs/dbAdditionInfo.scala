package hdfs

import java.net.URI

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}

/**
  * Created by btw on 2018/1/8.
  */
object dbAdditionInfo extends App {
  val fileSystem = FileSystem.get(new URI("hdfs://192.168.176.62:8020"), new Configuration())
  fileSystem.listStatus(new Path("/data/cloud/cif/cif_ods/mongodb")).map { db =>
    fileSystem.listStatus(db.getPath).flatMap {
      t =>
        try {
          fileSystem.listStatus(new Path(t.getPath.toString + "/cif_year=2017/")).flatMap { m =>
            fileSystem.listStatus(m.getPath).map {
              s =>
                (db.getPath.getName + "-" + m.getPath.getName ++ "-" + s.getPath.getName, fileSystem.getContentSummary(s.getPath).getLength)
            }
          }
        } catch {
          case e: Exception => Array(("null", 0L))
        }
    }.filter(_._1 != "null").groupBy(_._1).map {
      g =>
        (g._2.reduce((a, b) => (a._1, a._2 + b._2)))
    }.toList.sortBy(_._1).foreach(println(_))


  }
}
