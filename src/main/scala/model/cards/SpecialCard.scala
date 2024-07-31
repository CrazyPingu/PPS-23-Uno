package model.cards

import model.cards.special.{ChangeColor, DrawCard, ReverseCard, SkipCard}
import utils.Color
import utils.Color.Black

import java.awt.Image

/**
 * Represents a special card in the game.
 *
 * @param color The color of the card
 * @param image The image of the card
 */
abstract class SpecialCard(val color: Color, val image: Image) extends Card:

  override def toString: String = s"${color.toString} Special"

  /**
   * Executes the effect of the card.
   */
  def execute(): Unit
  