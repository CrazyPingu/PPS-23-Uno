package model.cards

import utils.Color

import java.awt.Image

/**
 * Represents a card in the game.
 * A card can be a simple card (with a number and a color) or a special card(with an effect).
 */
trait Card:

  /**
   * @return The color of the card
   */
  def color: Color

  /**
   * @return The image of the card
   */
  def image: Image
