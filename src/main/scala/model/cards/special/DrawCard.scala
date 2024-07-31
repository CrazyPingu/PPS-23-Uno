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
class DrawCard(private val cardColor: Color, val numberToDraw: Int)
    extends SpecialCard(cardColor, loadCardImage("Draw" + numberToDraw, cardColor)):

  override def toString: String = "Draw " + numberToDraw + " " + color.toString

  /**
   * The next player has to draw a certain number of cards from the deck.
   */
  override def execute(): Unit = GameController.nextDrawCard(numberToDraw)
