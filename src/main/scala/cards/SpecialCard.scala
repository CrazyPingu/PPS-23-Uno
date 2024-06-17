package cards

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
abstract class SpecialCard(private val color: Color, private val image: Image) extends Card:
  val cardColor: Color = color
  val cardImage: Image = image

  override def toString: String = s"${color.toString} Special"


