package model.cards

import utils.ImageHandler.loadCardImage
import utils.{CardNumber, Color}

import java.awt.Image

/**
 * Represents a simple card in the game.
 * A simple card is a card that has a number and a color.
 */
trait SimpleCard extends Card:

  val num: CardNumber

/**
 * Represents the simple card in the game.
 */
object SimpleCard:

  /**
   * Creates a simple card with the given number and color.
   *
   * @param num The number of the card
   * @param color The color of the card
   * @return A simple card with the given number and color
   */
  def apply(num: CardNumber, color: Color): SimpleCard =
    SimpleCardImpl(num, color, loadCardImage(num.value.toString, color))

/**
 * Represents a simple card in the game.
 *
 * @param num The number of the card
 * @param color The color of the card
 * @param image The image of the card
 */
private case class SimpleCardImpl(num: CardNumber, color: Color, image: Image) extends SimpleCard:
  override def toString: String = s"${color.toString} ${num.value}"
