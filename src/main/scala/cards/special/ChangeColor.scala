package cards.special

import cards.SpecialCard
import utils.Color

/**
 * Class that represents a card that has the effect of changing the color of the game.
 */
class ChangeColor extends SpecialCard(Color.Black):

  override def toString: String = "ChangeColor"
