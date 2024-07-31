package model.cards

import utils.Color

import java.awt.Image

/**
 * Represents a simple card in the game.
 * A simple card has a number and a color.
 *
 * @param num   The number of the card
 * @param color The color of the card
 * @param image image of the card
 */
class SimpleCardImpl(var num: Int, var color: Color, var image: Image) extends SimpleCard:

  override def toString: String = s"${color.toString} $num"