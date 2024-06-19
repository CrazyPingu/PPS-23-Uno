package model.cards.special

import model.cards.SpecialCard
import utils.Color
import utils.ImageHandler.loadCardImage

/**
 * Class that represents a card that has the effect of changing the color of the game.
 */
class ChangeColor extends SpecialCard(Color.Black, loadCardImage("Wild", Color.Black)):

  override def toString: String = "ChangeColor"
