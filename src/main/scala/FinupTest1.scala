import scala.collection.mutable.ArrayBuffer

/**
  * Created by btw on 2017/8/16.
  */
object FinupTest1 extends App {


  lazy val taskA =  println("taskA")
  val taskB1 = ("taskB1")
  val taskB2 = ("taskB2")
  val taskB3 = ("taskB3")
  val taskB4 = ("taskB4")
  val taskB5 = ("taskB5")
  val taskB6 = ("taskB6")
  val taskB7 = ("taskB7")
  lazy val taskC = println("taskC")


  val taskList = ArrayBuffer(taskB1, taskB2, taskB3, taskB4, taskB5, taskB6, taskB7)

  //执行A任务
  taskA
  println("============")
  //并行执行B任务
  taskList.par.foreach(println(_))
  println("============")
  //执行C任务
  taskC

}
