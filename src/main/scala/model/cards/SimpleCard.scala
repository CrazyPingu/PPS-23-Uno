package model.cards

import utils.CardNumber

/**
 * A simple card that has a number.
 */
trait SimpleCard extends Card:

  /**
   * @return The number of the card
   */
  def num: CardNumber

