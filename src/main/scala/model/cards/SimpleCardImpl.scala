package model.cards

import utils.Color

import java.awt.Image

/**
 * Represents a simple card in the game.
 * A simple card has a number and a color.
 *
 * @param cardNumber   The number of the card
 * @param cardColor The color of the card
 * @param cardImage The image of the card
 */
class SimpleCardImpl(private var cardNumber: Int, private var cardColor: Color, private var cardImage: Image) extends SimpleCard:
  val num: Int = cardNumber
  val color: Color = cardColor
  val image: Image = cardImage

  override def toString: String = s"${color.toString} $num"