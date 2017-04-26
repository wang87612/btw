package sql


import scala.util.parsing.combinator.syntactical.StandardTokenParsers

/**
  * Created by btw on 2017/1/3.
  */
object Test3 extends StandardTokenParsers with App {

  //def expr1 = repsep(expr2, rep("+" | "-"))

  def expr1: Parser[Int] = expr2 ~ rep("+" ~ expr2 | "-" ~ expr2) ^^ operation

  def expr2: Parser[Int] = expr3 ~ rep("*" ~ expr3 | "／" ~ expr3) ^^ operation

  def expr3: Parser[Int] = numericLit ^^ (_.toInt)

  def operation: Int ~ List[String ~ Int] => Int = {
    case i ~ ps => ps.foldLeft(i)((a, b) => reduce(a, b))
  }

  def reduce(x: Int, r: String ~ Int) = (r: @unchecked) match {
    case "+" ~ y => x + y
    case "-" ~ y => x - y
    case "*" ~ y => x * y
    case "/" ~ y => x / y
  }

  lexical.delimiters ++= List("+", "-", "*", "/", "(", ")")

  val str = "1+"


  val tokens = new lexical.Scanner(str)

  println(Test3.phrase(expr1)(tokens))

}
