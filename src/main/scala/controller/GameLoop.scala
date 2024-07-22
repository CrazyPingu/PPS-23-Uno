package controller

import model.bot.{BotPlayer, EasyBotPlayerImpl}
import model.cards.Card
import model.cards.factory.CardFactoryImpl
import model.{Deck, Hand}
import view.game.Gui

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Random

class GameLoop(private val controller: GameController, private val gui: Gui, val cardFactory: CardFactoryImpl):
  private var player: Hand = _
  private var bot1: BotPlayer = _
  private var bot2: BotPlayer = _
  private var bot3: BotPlayer = _
  private var turnOrder: List[Hand] = _
  private var currentTurn = 0
  private var clockWiseDirection = true
  private var isRunning = false

  def start(): Unit =
    val deck = Deck(cardFactory)
    player = Hand()
    val bots = createBotPlayers()
    bot1 = bots(0)
    bot2 = bots(1)
    bot3 = bots(2)
    currentTurn = 0
    clockWiseDirection = true
    isRunning = true
    turnOrder = List(player, bot1, bot2, bot3)

    gui.setEntity(bot1, bot2, bot3, player)
    controller.startNewGame(player, deck)

    giveStartingCards(bot1, bot2, bot3, player, deck)
    gui.updateTurnArrow(currentTurn)

  def nextTurn(): Unit =
    if !isRunning then return
    Future:
      currentTurn = (currentTurn + (if clockWiseDirection then 1 else -1) + turnOrder.size) % turnOrder.size
      gui.updateTurnArrow(currentTurn)
      turnOrder(currentTurn) match
        case bot: BotPlayer =>
          Thread.sleep((1500 + Random.nextInt(1500)).toLong)
          bot.chooseCardToUse(controller.lastPlayedCard.get) match
            case Some(card) =>
              controller.chooseCard(card, bot)
            case None =>
              controller.drawCard(bot)
        case _ =>
          controller.checkUno()
          gui.allowPlayerAction(true)

  def reverseTurnOrder(): Unit = clockWiseDirection = !clockWiseDirection

  def currentPlayer: Hand = turnOrder(currentTurn)

  def nextDrawCard(numberToDraw: Int): Unit =
    val nextPlayer = turnOrder((currentTurn + (if clockWiseDirection then 1 else -1) + turnOrder.size) % turnOrder.size)
    controller.drawCard(nextPlayer, numberToDraw, false)

  def skipNextTurn(numberToSkip: Int): Unit =
    if clockWiseDirection then currentTurn = (currentTurn + numberToSkip + turnOrder.size) % turnOrder.size
    else currentTurn = (currentTurn - numberToSkip - turnOrder.size + turnOrder.size) % turnOrder.size

  def stop(): Unit = isRunning = false

  private def createBotPlayers(): (BotPlayer, BotPlayer, BotPlayer) =
    (new EasyBotPlayerImpl, new EasyBotPlayerImpl, new EasyBotPlayerImpl)

  /**
   * Give starting cards to all players
   */
  private def giveStartingCards(
    bot1: BotPlayer,
    bot2: BotPlayer,
    bot3: BotPlayer,
    player: Hand,
    deck: Deck,
    numToDraw: Int = 7
  ): Unit =
    for _ <- 0 until numToDraw do
      bot1.addCard(deck.draw())
      bot2.addCard(deck.draw())
      bot3.addCard(deck.draw())
      player.addCard(deck.draw())

    gui.updateGui()
