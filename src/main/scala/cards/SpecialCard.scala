package cards

import utils.Color

/**
 * Represents a special card in the game.
 *
 * @param color The color of the card
 */
abstract class SpecialCard(private val color: Color) extends Card:
  val cardColor: Color = color

  override def toString: String = s"${color.toString} Special"


