package model.cards

import utils.ImageHandler.loadCardImage
import utils.{CardNumber, Color}

/**
 * A simple card that has a number.
 */
trait SimpleCard extends Card:

  /**
   * @return The number of the card
   */
  def num: CardNumber

object SimpleCard:
  def apply(num: CardNumber, color: Color): SimpleCard =
    SimpleCardImpl(num, color, loadCardImage(num.value.toString, color))
