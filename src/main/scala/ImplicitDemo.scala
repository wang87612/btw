/**
  * Created by btw on 2016/12/30.
  */
object ImplicitDemo extends App {

  def display(input: String, input2: String): Unit = println(input)

  implicit def typeConvertor(input: Int): String = input.toString

  implicit def typeConvertor(input: Boolean): String = if (input) "true" else "false"

  display("1212", "aa")
  display(12, 12)
  display(true, true)

  //-----------------------------
  def calcTax(amount: Float)(implicit rate: Float): Float = amount * rate

  implicit val currentTaxRate = 0.08F
  val tax = calcTax(50000F) // 4000.0
  println(tax)


}
