package model.cards.special

import controller.Controller
import model.cards.SpecialCard
import utils.Color
import utils.ImageHandler.loadCardImage

/**
 * Represents a card that has the effect of skipping the next player's turn.
 *
 * @param cardColor    The color of the card
 * @param numberToSkip The number of players to skip
 */
class SkipCard(private val cardColor: Color, private val numberToSkip: Int, private val controller: Controller)
    extends SpecialCard(cardColor, loadCardImage("Skip", cardColor), controller):

  override def toString: String = "Skip " + color.toString

  /**
   * @return The number of players to skip
   */
  def getNumberToSkip: Int = numberToSkip

  /**
   * The next player has to skip a certain number of turns.
   */
  override def execute(): Unit = println("Skip " + numberToSkip + " players")
