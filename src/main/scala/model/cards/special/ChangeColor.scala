package model.cards.special

import controller.GameController
import model.cards.SpecialCard
import utils.Color
import utils.ImageHandler.loadCardImage

/**
 * Class that represents a card that has the effect of changing the color of the game.
 */
class ChangeColor(private val controller: GameController, private var cardColor: Color = Color.Black)
    extends SpecialCard(cardColor, loadCardImage("Wild", cardColor), controller):

  override def toString: String = "ChangeColor"

  /**
   * Changes the color of the card that is being played.
   */
  override def execute(): Unit = controller.showChangeColor()
