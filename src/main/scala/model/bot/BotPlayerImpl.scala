package model.bot

import model.cards.{Card, SimpleCard, SpecialCard}
import utils.Color

abstract class BotPlayerImpl extends BotPlayer:
  protected def isCompatible(selectedCard: Card, centerCard: Card): Boolean =
    selectedCard match
      case s: SpecialCard if s.color == Color.Black => true
      case s: SimpleCard if s.num == centerCard.asInstanceOf[SimpleCard].num => true
      case c: Card if c.color == centerCard.color => true
      case _: SpecialCard if selectedCard.getClass == centerCard.getClass => true
      case _ => false