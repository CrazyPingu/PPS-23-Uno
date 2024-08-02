package model.bot
import model.cards.{Card, SpecialCard}

/**
 * A class representing an hard bot player.
 *
 * An hard bot player plays the first compatible Special card it finds in its hand,
 * otherwise the first compatible Simple Card it finds in its hand, none otherwise.
 */
class HardBotPlayerImpl extends BotPlayerImpl:
  override def chooseCardToUse(centerCard: Card): Option[Card] =
    val compatibleCards = this.filter(
      c => isCompatible(c, centerCard)
    )
    if compatibleCards.nonEmpty then
      val specialCards = compatibleCards.filter(
        c => c.isInstanceOf[SpecialCard]
      )
      if specialCards.nonEmpty then
        val card = specialCards.head
        Some(card)
      else
        val card = compatibleCards.head
        Some(card)
    else None
