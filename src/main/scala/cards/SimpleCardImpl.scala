package cards

import utils.Color

/**
 * Represents a simple card in the game.
 * A simple card has a number and a color.
 *
 * @param num   The number of the card
 * @param color The color of the card
 */
class SimpleCardImpl(private var num: Int, private var color: Color) extends SimpleCard:
  val number: Int = num
  val cardColor: Color = color

  override def toString: String = s"${color.toString} $num"