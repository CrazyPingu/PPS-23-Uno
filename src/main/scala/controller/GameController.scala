package controller

import model.bot.BotPlayer
import model.cards.factory.CardFactory
import model.cards.special.{ChangeColor, DrawCard}
import model.cards.{Card, SpecialCard}
import model.{Deck, Hand}
import utils.Color
import utils.Compatibility.isCompatible
import view.{CardLayoutId, Frame}
import view.game.Gui

class GameController(private val pageController: PageController, private val cardFactory: CardFactory):
  private var gui: Option[Gui] = None
  private var gameLoop: Option[GameLoop] = None
  private var deck: Option[Deck] = None
  private var playerHand: Option[Hand] = None
  var lastPlayedCard: Option[Card] = None
  private var unoCalled = false

  def drawCard(hand: Hand = playerHand.get, num: Int = 1, skipTurn: Boolean = true): Unit =
    gui.get.allowPlayerAction(false)
    for _ <- 0 until num do hand.addCard(deck.get.draw())
    gui.get.updateGui()
    if skipTurn then gameLoop.get.nextTurn()

  def setGuiAndGameLoop(gui: Gui, gameLoop: GameLoop): Unit =
    this.gui = Some(gui)
    this.gameLoop = Some(gameLoop)

  def startNewGame(playerHand: Hand, deck: Deck): Unit =
    this.playerHand = Some(playerHand)
    this.deck = Some(deck)
    lastPlayedCard = Some(deck.draw())
    this.gui.get.disposeCard(this.lastPlayedCard.get)
    this.unoCalled = false
    gui.get.setUnoButtonChecked(false)

  def chooseCard(card: Card, hand: Hand = playerHand.get): Unit =
    if isCompatible(card, lastPlayedCard.get) then
      gui.get.allowPlayerAction(false)
      disposeCard(card)
      hand.removeCard(card)
      checkIfSpecialCard(card)

      if playerHand.get.isEmpty then pageController.showWin()
      else if hand.isEmpty then pageController.showLose()

      if card.color != Color.Black then
        gui.get.updateGui()
        gameLoop.get.nextTurn()
    else println("Card not compatible " + card + "\nLast played card " + lastPlayedCard.get)

  def reverseDirection(): Unit =
    gameLoop.get.reverseTurnOrder()
    gui.get.reverseDirection()

  def nextDrawCard(numberToDraw: Int): Unit =
    gameLoop.get.nextDrawCard(numberToDraw)
    if lastPlayedCard.get.color == Color.Black then showChangeColor()

  def skipNextTurn(numberToSkip: Int): Unit =
    gameLoop.get.skipNextTurn(numberToSkip)

  def showChangeColor(): Unit =
    if gameLoop.get.currentPlayer == playerHand.get then pageController.showChangeColor()
    else
      val color = gameLoop.get.currentPlayer.asInstanceOf[BotPlayer].chooseColor()
      changeColor(color)

  /**
   * Change the color of the last played card
   * If the last played card is a DrawCard, the next card will have to play the new color
   *
   * @param color The new color chosen by the player
   */
  def changeColor(color: Color): Unit =
    lastPlayedCard.get match
      case _: ChangeColor => lastPlayedCard = Some(cardFactory.createChangeColor(color))
      case _: DrawCard    => lastPlayedCard = Some(cardFactory.createDrawCard(4, color))
      case _              =>

    disposeCard(lastPlayedCard.get)
    pageController.showGame(false)
    gameLoop.get.nextTurn()

  def callUno(): Unit =
    if playerHand.get.size == 1 then
      unoCalled = true
      gui.get.setUnoButtonChecked(true)

  def checkUno(): Unit =
    if !unoCalled && playerHand.get.size == 1 then
      println("You didn't call UNO!")
      drawCard(playerHand.get, 1, false)
    else if unoCalled then println("Called UNO correctly!")
    unoCalled = false
    gui.get.setUnoButtonChecked(false)

  private def checkIfSpecialCard(card: Card): Unit =
    card match
      case c: SpecialCard => c.execute()
      case _              => ()

  private def disposeCard(card: Card): Unit =
    lastPlayedCard = Some(card)
    gui.get.disposeCard(card)
    gui.get.updateGui()
