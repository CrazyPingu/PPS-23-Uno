package model.bot
import model.cards.{Card, SimpleCard, SpecialCard}

class HardBotPlayerImpl extends BotPlayerImpl:
  override def chooseCardToUse(centerCard: Card): Option[Card] =
    val compatibleCards = this.filter(c => isCompatible(c, centerCard))
    if compatibleCards.nonEmpty then
      val specialCards = compatibleCards.filter(c => c.isInstanceOf[SpecialCard])
      if specialCards.nonEmpty then
        val card = specialCards.head
        this.removeCard(card)
        Some(card)
      else
        val card = compatibleCards.head
        this.removeCard(card)
        Some(card)
    else
      None