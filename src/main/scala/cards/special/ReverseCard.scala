package cards.special

import cards.SpecialCard
import utils.Color

/**
 * Represents a card that has the effect of reversing the turn order.
 *
 * @param color The color of the card
 */
class ReverseCard(private val color: Color) extends SpecialCard(color):

  override def toString: String = "Reverse " + color.toString

