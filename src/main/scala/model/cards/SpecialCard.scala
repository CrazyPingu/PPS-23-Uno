package model.cards

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
abstract class SpecialCard(private val cardColor: Color, private val cardImage: Image) extends Card:
  val color: Color = cardColor
  val image: Image = cardImage

  override def toString: String = s"${color.toString} Special"


