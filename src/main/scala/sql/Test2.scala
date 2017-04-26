package sql

import scala.util.parsing.combinator.JavaTokenParsers

/**
  * Created by btw on 2017/1/3.
  */
object Test2 extends JavaTokenParsers with App {

  //定义一个表达式 如果是一个Int 则 返回一个Double
  def num = floatingPointNumber ^^ (i => "int")

  //定义一个表达式 如果是一个String 则 返回一个字符串
  def str = stringLiteral ^^ (s => "string")

  //对数字和字符串转换 | 是或者的意思 如果是数字则返回double 或者 如果是一个字符串则返回字符串
  def factor = num | str

  //去掉两边的括号 然后返回口号中间的东西 然后根据类型返回数据
  def expr = "(" ~ factor ~ ")" ^^ { case (a ~ b ~ c) => b }

  //去掉两边的括号 然后返回口号中间的东西 然后根据类型返回数据
  def expr1 = "(" ~> factor <~ ")" ^^ (x => x)

  //去掉N个逗号得到的每一个值 去调用factor
  def expr2 = rep(factor <~ ",*".r)

  //去掉N个逗号得到的每一个值 去调用factor
  def expr3 = repsep(factor, ",")

  println(Test2.parse(Test2.expr3, """ "a","b",1,2,3 """))


}
