package utils

import model.cards.special.DrawCard
import model.cards.{Card, SimpleCard}

object Compatibility:

  def isCompatible(selectedCard: Card, centerCard: Card): Boolean =
    (selectedCard, centerCard) match
      case (s, c) if s.color == Color.Black || c.color == Color.Black        => true
      case (s, c) if s.color == c.color                                      => true
      case (s: SimpleCard, c: SimpleCard) if s.num == c.num                  => true
      case (s : DrawCard, c : DrawCard) if s.numberToDraw == c.numberToDraw  => true
      case (s, c) if s.getClass == c.getClass && !s.isInstanceOf[SimpleCard] && !s.isInstanceOf[DrawCard]=> true
      case _                                                                 => false
