/**
  * Created by btw on 2017/4/27.
  */
object Basis extends App {

  def showCapital(x: Option[String]) = x match {
    case Some(s) => s
    case None => "?"
  }

  val capitals = Map("France"->"Paris", "Japan"->"Tokyo", "China"->"Beijing")

  println(capitals get "France")
  println(capitals get "France1")


  println(showCapital(capitals get "France1"))

}
