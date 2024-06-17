package cards

import utils.Color

import java.awt.Image

/**
 * Represents a card in the game.
 * A card can be a simple card (with a number and a color) or a special card(with an effect).
 */
trait Card:
  def cardColor: Color
  def cardImage : Image

  /**
   * @return The color of the card
   */
  def getColor: Color = cardColor

  /**
   * @return The image of the card
   */
  def getImage: Image = cardImage
