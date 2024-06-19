import model.cards.SimpleCardImpl
import utils.Color.Red
import utils.ImageHandler.loadCardImage

object main:

  def main(args: Array[String]): Unit =
    val card = new SimpleCardImpl(5, utils.Color.Red, loadCardImage("5", Red))
    println(card)

    for _ <- 0 to 1 do
      println("Hello, world!")


