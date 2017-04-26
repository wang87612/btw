import java.io.{BufferedReader, File, FileReader}

/**
  * Created by wangpeng on 2016/12/5.
  */
object Implicit extends App {



  implicit class Files(file: File) {
    def lines: Array[String] = {
      val fileReader: FileReader = new FileReader(file)
      val reader = new BufferedReader(fileReader)
      try {
        var lines = Array[String]()
        var line = reader.readLine()

        while (line != null) {
          lines = lines :+ line
          line = reader.readLine()
        }
        lines
      } finally {
        fileReader.close()
        reader.close()
      }
    }
  }

  private val file: File = new File("Implicit")

  file.lines foreach print
}
