import scala.util.parsing.combinator.syntactical.StandardTokenParsers

class Expr

//认为所有计算都是一个操作符和2个数字
case class Op(op: String, l: Expr, r: Expr) extends Expr

case class Num(n: Int) extends Expr

object ArithmeticParsers2 extends StandardTokenParsers {

  //分隔符
  lexical.delimiters ++= List("(", ")", "+", "-", "*", "/")

  val reduceList: Expr ~ List[String ~ Expr] => Expr = {
    case i ~ ps => (i /: ps) (reduce)
  }

  def reduce(l: Expr, r: String ~ Expr) = Op(r._1, l, r._2)

  //string to int
  def mkNum(s: String) = Num(s.toInt)

  def expr: Parser[Expr] = term ~ rep("+" ~ term | "-" ~ term) ^^ reduceList

  def term: Parser[Expr] = factor ~ rep("*" ~ factor | "/" ~ factor) ^^ reduceList

  def factor: Parser[Expr] = "(" ~> expr <~ ")" | numericLit ^^ ((s: String) => Num(s.toInt))

  def arithmetic(expr: Expr): Int = expr match {
    case Num(i) => i
    case Op("+", x, y) => arithmetic(x) + arithmetic(y)
    case Op("-", x, y) => arithmetic(x) - arithmetic(y)
    case Op("*", x, y) => arithmetic(x) * arithmetic(y)
    case Op("/", x, y) => arithmetic(x) / arithmetic(y)
  }

    def main(args: Array[String]) {

    //得到一个解析器
    val parse = phrase(expr)
    //表达式
    val str = "1+2"
    //进行切词
    val tokens = new lexical.Scanner(str)
    //用解析器进行解析
    val kankan = parse(tokens).get


    //计算
    println(arithmetic(kankan))

  }
}