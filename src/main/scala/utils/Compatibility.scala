package utils

import model.cards.{Card, SimpleCard}

/**
 * Object that contains the compatibility logic between the selected card and the center card.
 */
object Compatibility:

  /**
   * Check if the selected card is compatible with the center card.
   *
   * @param selectedCard the card selected by the player.
   * @param centerCard the card in the center of the table.
   * @return true if the selected card is compatible with the center card, false otherwise.
   */
  def isCompatible(selectedCard: Card, centerCard: Card): Boolean =
    (selectedCard, centerCard) match
      case (s, c) if s.color == Color.Black || c.color == Color.Black        => true
      case (s, c) if s.color == c.color                                      => true
      case (s: SimpleCard, c: SimpleCard) if s.num == c.num                  => true
      case (s, c) if s.getClass == c.getClass && !s.isInstanceOf[SimpleCard] => true
      case _                                                                 => false
