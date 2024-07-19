package model.cards.special

import controller.GameController
import model.cards.SpecialCard
import utils.Color
import utils.ImageHandler.loadCardImage

/**
 * Class that represents a card that has the effect of drawing a certain number of cards from the deck.
 *
 * @param cardColor    The color of the card
 * @param numberToDraw The number of cards to draw
 */
class DrawCard(private val cardColor: Color, private val numberToDraw: Int, private val controller: GameController)
    extends SpecialCard(cardColor, loadCardImage("Draw", cardColor), controller):

  override def toString: String = "Draw " + numberToDraw + " " + color.toString

  /**
   * @return The number of cards to draw
   */
  def getNumberToDraw: Int = numberToDraw

  /**
   * The next player has to draw a certain number of cards from the deck.
   */
  override def execute(): Unit = controller.nextDrawCard(numberToDraw)
