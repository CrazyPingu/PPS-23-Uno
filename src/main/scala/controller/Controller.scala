package controller

import model.cards.Card
import model.{Deck, Hand}
import utils.Compatibility.isCompatible
import view.game.Gui

class Controller:
  private var gui: Option[Gui] = None
  private var deck: Option[Deck] = None
  private var hand: Option[Hand] = None
  private var lastPlayedCard: Card = null

  def drawCard(): Unit =
    hand.get.addCard(deck.get.draw())
    gui.get.updateGui()

  def setGui(gui: Gui): Unit =
    this.gui = Some(gui)

  def startNewGame(hand: Hand, deck: Deck, lastPlayedCard: Card): Unit =
    this.hand = Some(hand)
    this.deck = Some(deck)
    this.lastPlayedCard = lastPlayedCard

  def chooseCard(card: Card): Unit =
    if isCompatible(card, lastPlayedCard) then
      hand.get.removeCard(card)
      gui.get.updateGui()
      gui.get.disposeCard(card)
