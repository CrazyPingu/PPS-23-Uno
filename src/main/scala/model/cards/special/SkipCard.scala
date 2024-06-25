package model.cards.special

import model.cards.SpecialCard
import utils.Color
import utils.ImageHandler.loadCardImage

/**
 * Represents a card that has the effect of skipping the next player's turn.
 *
 * @param cardColor    The color of the card
 * @param numberToSkip The number of players to skip
 */
class SkipCard(private val cardColor: Color, private val numberToSkip: Int) extends SpecialCard(cardColor, loadCardImage("Skip", cardColor)):

  override def toString: String = "Skip " + color.toString

  /**
   * @return The number of players to skip
   */
  def getNumberToSkip: Int = numberToSkip