package model.bot

import model.cards.{Card, SimpleCard, SimpleCardImpl, SpecialCard}

class EasyBotPlayerImpl extends BotPlayerImpl :
  override def chooseCardToUse(centerCard: Card): Option[Card] = 
    val compatibleCards = this.filter(c => isCompatible(c, centerCard))
    if compatibleCards.nonEmpty then
      val card = compatibleCards.head
      this.removeCard(card)
      Some(card)
    else
      None