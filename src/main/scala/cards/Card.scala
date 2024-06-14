package cards

import utils.Color

/**
 * Represents a card in the game.
 * A card can be a simple card (with a number and a color) or a special card(with an effect).
 */
trait Card:

  /**
   * @return The color of the card
   */
  def getColor: Color
