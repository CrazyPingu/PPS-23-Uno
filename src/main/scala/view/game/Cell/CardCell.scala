package view.game.Cell

import model.cards.Card

/**
 * A cell that contains a card
 *
 * @param card the card to be displayed
 */
class CardCell(card: Card) extends Cell:
  setIcon(card.image)

  addActionListener(_ => println("CardCell clicked! " + card.toString))



