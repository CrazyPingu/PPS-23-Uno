package model.bot

import model.cards.Card

/**
 * A class representing an easy bot player.
 *
 * An easy bot player plays the first compatible card it finds in its hand.
 */
class EasyBotPlayerImpl extends BotPlayerImpl:
  override def chooseCardToUse(centerCard: Card): Option[Card] =
    val compatibleCards = this.filter(
      c => isCompatible(c, centerCard)
    )
    if compatibleCards.nonEmpty then
      val card = compatibleCards.head
      Some(card)
    else None
