package model.cards.special

import model.cards.SpecialCard
import utils.Color
import utils.ImageHandler.loadCardImage

/**
 * Class that represents a card that has the effect of drawing a certain number of cards from the deck.
 * 
 * @param color The color of the card
 * @param numberToDraw The number of cards to draw
 */
class DrawCard(private val color: Color, private val numberToDraw: Int) extends SpecialCard(color, loadCardImage("Draw", color)):

  override def toString: String = "Draw " + numberToDraw + " " + color.toString

  /**
   * @return The number of cards to draw
   */
  def getNumberToDraw: Int = numberToDraw
