package cards.special

import cards.SpecialCard
import utils.Color

/**
 * Represents a card that has the effect of skipping the next player's turn.
 *
 * @param color The color of the card
 * @param numberToSkip The number of players to skip
 */
class SkipCard(private val color: Color, private val numberToSkip: Int) extends SpecialCard(color):

  override def toString: String = "Skip " + color.toString

  /**
   * @return The number of players to skip
   */
  def getNumberToSkip: Int = numberToSkip
