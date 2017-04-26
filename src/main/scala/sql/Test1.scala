import scala.util.parsing.combinator.syntactical.StandardTokenParsers

object Test1 extends StandardTokenParsers {

  //定义了分词符号
  lexical.delimiters ++= List("(", ")", "+", "-", "*", "/")

  val reduceList: Int ~ List[String ~ Int] => Int = {
    case i ~ ps => (i /: ps) (reduce)
  }

  def reduce(x: Int, r: String ~ Int) = (r: @unchecked) match {
    case "+" ~ y => x + y
    case "-" ~ y => x - y
    case "*" ~ y => x * y
    case "/" ~ y => x / y
  }

  def expr: Parser[Int] = term ~ rep("+" ~ term | "-" ~ term) ^^ reduceList

  def term: Parser[Int] = factor ~ rep("*" ~ factor | "/" ~ factor) ^^ reduceList

  def factor: Parser[Int] = "(" ~> expr <~ ")" | numericLit ^^ (_.toInt)

  def main(args: Array[String]) {
    val str = "1+2-1-2-3+6"
    val tokens = new lexical.Scanner(str)
    println(phrase(expr)(tokens))
  }
}