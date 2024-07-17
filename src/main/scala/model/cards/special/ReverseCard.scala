package model.cards.special

import controller.Controller
import model.cards.SpecialCard
import utils.Color
import utils.ImageHandler.loadCardImage

/**
 * Represents a card that has the effect of reversing the turn order.
 *
 * @param cardColor The color of the card
 */
class ReverseCard(private val cardColor: Color, private val controller: Controller)
    extends SpecialCard(cardColor, loadCardImage("Reverse", cardColor), controller):

  override def toString: String = "Reverse " + color.toString

  /**
   * Reverses the turn order.
   */
  override def execute(): Unit = controller.reverseDirection()
