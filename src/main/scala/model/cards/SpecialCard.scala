package model.cards

import controller.GameController
import utils.Color

import java.awt.Image
import java.io.{File, IOException}
import javax.imageio.ImageIO

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
