package controller

import model.cards.{Card, SpecialCard}
import model.{Deck, Hand}
import utils.Compatibility.isCompatible
import view.game.Gui

class Controller:
  private var gui: Option[Gui] = None
  private var gameLoop: Option[GameLoop] = None
  private var deck: Option[Deck] = None
  private var playerHand: Option[Hand] = None
  var lastPlayedCard: Option[Card] = None

  def drawCard(hand: Hand): Unit =
    drawCard(hand, 1, true)

  def drawCard(hand: Hand, num: Int, skipTurn: Boolean): Unit =
    gui.get.allowPlayerAction(false)
    for _ <- 0 until num do hand.addCard(deck.get.draw())
    gui.get.updateGui()
    if skipTurn then gameLoop.get.nextTurn()

  def drawCard(): Unit =
    drawCard(playerHand.get)

  def setGuiAndGameLoop(gui: Gui, gameLoop: GameLoop): Unit =
    this.gui = Some(gui)
    this.gameLoop = Some(gameLoop)

  def startNewGame(playerHand: Hand, deck: Deck): Unit =
    this.playerHand = Some(playerHand)
    this.deck = Some(deck)
    lastPlayedCard = Some(deck.draw())
    this.gui.get.disposeCard(this.lastPlayedCard.get)

  def chooseCard(card: Card, hand: Hand): Unit =
    if isCompatible(card, lastPlayedCard.get) then
      gui.get.allowPlayerAction(false)
      lastPlayedCard = Some(card)
      gui.get.disposeCard(card)
      hand.removeCard(card)
      checkIfSpecialCard(card)
      gui.get.updateGui()
      gameLoop.get.nextTurn()
    else println("Card not compatible " + card + "\nLast played card " + lastPlayedCard.get)

    if playerHand.get.isEmpty then println("You have won")
    else if hand.isEmpty then println("You have lost")

  def chooseCard(card: Card): Unit =
    chooseCard(card, playerHand.get)

  def reverseDirection(): Unit =
    gameLoop.get.reverseTurnOrder()
    gui.get.reverseDirection()

  def nextDrawCard(numberToDraw: Int): Unit =
    gameLoop.get.nextDrawCard(numberToDraw)

  def skipNextTurn(numberToSkip: Int): Unit =
    gameLoop.get.skipNextTurn(numberToSkip)

  private def checkIfSpecialCard(card: Card): Unit =
    card match
      case c: SpecialCard => c.execute()
      case _              => ()
