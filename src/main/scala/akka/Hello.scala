package akka

import akka.actor.{Actor, ActorSystem, Props}
import scala.sys.process._

/**
  * Created by btw on 2018/8/2.
  */
class HelloActor extends Actor {
  def receive = {
    //导历史数据
    case (dbName: String) => {
      println(dbName)
      context.parent ! "okokokok"
      self ! ("1111", "h222ello")
    }
    case (dbName: String, table: String) => {
      println(dbName)
    }
    case _ => null
  }
}

object Test1_HelloActor extends App {
  // actor need an ActorSystem
  val system = ActorSystem("HelloSystem")
  // create and start the actor
  val helloActor = system.actorOf(Props[HelloActor], name = "helloActor")
  // send two messages
  helloActor ! "hello"



  // shutdown the actor system
  system.shutdown
}