import utils.Color.Red
import utils.ImageHandler.loadCardImage

object main:

  def main(args: Array[String]) =
    val card = new cards.SimpleCardImpl(5, utils.Color.Red, loadCardImage("5", Red))
    println(card)


