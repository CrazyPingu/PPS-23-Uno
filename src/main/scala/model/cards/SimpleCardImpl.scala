package model.cards

import utils.Color

import java.awt.Image

/**
 * Represents a simple card in the game.
 * A simple card has a number and a color.
 *
 * @param num   The number of the card
 * @param color The color of the card
 * @param image The image of the card
 */
class SimpleCardImpl(private var num: Int, private var color: Color, private var image: Image) extends SimpleCard:
  val number: Int = num
  val cardColor: Color = color
  val cardImage: Image = image

  override def toString: String = s"${color.toString} $num"