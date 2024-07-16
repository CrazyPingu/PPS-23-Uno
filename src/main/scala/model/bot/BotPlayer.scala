package model.bot

import model.Hand
import model.cards.Card

trait BotPlayer extends Hand:
  def chooseCardToUse(card: Card): Option[Card]