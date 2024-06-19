package model.cards.special

import model.cards.SpecialCard
import utils.Color
import utils.ImageHandler.loadCardImage

/**
 * Represents a card that has the effect of reversing the turn order.
 *
 * @param cardColor The color of the card
 */
class ReverseCard(private val cardColor: Color) extends SpecialCard(cardColor, loadCardImage("Reverse", cardColor)):

  override def toString: String = "Reverse " + color.toString

