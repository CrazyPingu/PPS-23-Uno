package model.bot

import model.cards.{Card, SimpleCard, SpecialCard}
import utils.{Color, Compatibility}

abstract class BotPlayerImpl extends BotPlayer:
  protected def isCompatible(selectedCard: Card, centerCard: Card): Boolean =
    Compatibility.isCompatible(selectedCard, centerCard)
