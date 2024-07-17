package model.cards

import controller.Controller
import utils.Color

import java.awt.Image
import java.io.{File, IOException}
import javax.imageio.ImageIO

/**
 * Represents a special card in the game.
 *
 * @param cardColor The color of the card
 * @param cardImage The image of the card
 */
abstract class SpecialCard(
  private val cardColor: Color,
  private val cardImage: Image,
  private val controller: Controller
) extends Card:
  val color: Color = cardColor
  val image: Image = cardImage

  override def toString: String = s"${color.toString} Special"

  /**
   * Executes the effect of the card.
   */
  def execute(): Unit
