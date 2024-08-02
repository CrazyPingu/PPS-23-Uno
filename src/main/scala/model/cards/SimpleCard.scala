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

object SimpleCard:
  def apply(num: CardNumber, color: Color): SimpleCard =
    SimpleCardImpl(num, color, loadCardImage(num.value.toString, color))

private case class SimpleCardImpl(num: CardNumber, color: Color, image: Image) extends SimpleCard:
  override def toString: String = s"${color.toString} ${num.value}"
