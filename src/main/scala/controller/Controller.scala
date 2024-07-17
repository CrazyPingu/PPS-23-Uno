package controller

import model.cards.Card
import model.{Deck, Hand}
import utils.Compatibility.isCompatible
import view.game.Gui

class Controller:
  private var gui: Option[Gui] = None
  private var deck: Option[Deck] = None
  private var hand: Option[Hand] = None
  private var lastPlayedCard: Option[Card] = None

  def drawCard(): Unit =
    hand.get.addCard(deck.get.draw())
    gui.get.updateGui()

  def setGui(gui: Gui): Unit =
    this.gui = Some(gui)

  def startNewGame(hand: Hand, deck: Deck): Unit =
    this.hand = Some(hand)
    this.deck = Some(deck)
    this.lastPlayedCard = Some(deck.draw())
    this.gui.get.disposeCard(lastPlayedCard.get)

  def chooseCard(card: Card): Unit =
    if isCompatible(card, lastPlayedCard.get) then
      lastPlayedCard = Some(card)
      gui.get.disposeCard(card)
      hand.get.removeCard(card)
      gui.get.updateGui()
