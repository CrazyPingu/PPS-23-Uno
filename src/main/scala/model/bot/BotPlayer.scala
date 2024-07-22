package model.bot

import model.Hand
import model.cards.Card
import utils.Color

trait BotPlayer extends Hand:
  def chooseCardToUse(card: Card): Option[Card]

  /**
   * Finds the most frequent color in the hand.
   *
   * @return The color that is most present in the hand.
   */
  def chooseColor(): Color
