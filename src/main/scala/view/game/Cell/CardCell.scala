package view.game.Cell

import model.cards.Card

class CardCell(card: Card) extends Cell:
  setIcon(card.image)

  addActionListener(_ => println("CardCell clicked! " + card.toString))



