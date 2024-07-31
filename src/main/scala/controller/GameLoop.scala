package controller

import model.bot.{BotPlayer, EasyBotPlayerImpl, HardBotPlayerImpl}
import model.cards.Card
import model.settings.Difficulty.Difficulty
import model.settings.{Difficulty, GameSettings}
import model.{Deck, Hand}
import view.game.Gui

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Random

object GameLoop:
  private var player: Hand = _
  private var bot1: BotPlayer = _
  private var bot2: BotPlayer = _
  private var bot3: BotPlayer = _
  private var turnOrder: List[Hand] = _
  private var currentTurn = 0
  private var clockWiseDirection = true
  private var isRunning = false

  def start(): Unit =
    val currentSettings: GameSettings = SettingsController.settings.gameSettings

    val deck = Deck()
    player = Hand()
    val bots = createBotPlayers(currentSettings.difficulty)
    bot1 = bots(0)
    bot2 = bots(1)
    bot3 = bots(2)
    currentTurn = 0
    clockWiseDirection = true
    isRunning = true
    turnOrder = List(player, bot1, bot2, bot3)

    Gui.setEntity(bot1, bot2, bot3, player)
    GameController.startNewGame(player, deck)

    giveStartingCards(bot1, bot2, bot3, player, deck, currentSettings.startCardValue, currentSettings.handicap)
    Gui.updateTurnArrow(currentTurn)

  def nextTurn(): Unit =
    if !isRunning then return
    Future:
      currentTurn = (currentTurn + (if clockWiseDirection then 1 else -1) + turnOrder.size) % turnOrder.size
      Gui.updateTurnArrow(currentTurn)
      turnOrder(currentTurn) match
        case bot: BotPlayer =>
          Thread.sleep((1500 + Random.nextInt(1500)).toLong)
          bot.chooseCardToUse(GameController.lastPlayedCard.get) match
            case Some(card) =>
              GameController.chooseCard(card, bot)
            case None =>
              GameController.drawCard(bot)
        case _ =>
          GameController.checkUno()
          Gui.allowPlayerAction(true)

  def reverseTurnOrder(): Unit = clockWiseDirection = !clockWiseDirection

  def currentPlayer: Hand = turnOrder(currentTurn)

  def nextDrawCard(numberToDraw: Int): Unit =
    val nextPlayer = turnOrder((currentTurn + (if clockWiseDirection then 1 else -1) + turnOrder.size) % turnOrder.size)
    GameController.drawCard(nextPlayer, numberToDraw, false)

  def skipNextTurn(numberToSkip: Int): Unit =
    if clockWiseDirection then currentTurn = (currentTurn + numberToSkip + turnOrder.size) % turnOrder.size
    else currentTurn = (currentTurn - numberToSkip - turnOrder.size + turnOrder.size) % turnOrder.size

  def stop(): Unit = isRunning = false

  private def createBotPlayers(difficulty: Difficulty): (BotPlayer, BotPlayer, BotPlayer) = difficulty match
    case Difficulty.Easy => (new EasyBotPlayerImpl, new EasyBotPlayerImpl, new EasyBotPlayerImpl)
    case Difficulty.Hard => (new HardBotPlayerImpl, new HardBotPlayerImpl, new HardBotPlayerImpl)
    case _               => throw new IllegalArgumentException("Unsupported difficulty level")

  /**
   * Give starting cards to all players
   */
  private def giveStartingCards(
    bot1: BotPlayer,
    bot2: BotPlayer,
    bot3: BotPlayer,
    player: Hand,
    deck: Deck,
    numToDraw: Int = 7,
    handicap: Int = 0
  ): Unit =
    for _ <- 0 until numToDraw + handicap do
      bot1.addCard(deck.draw())
      bot2.addCard(deck.draw())
      bot3.addCard(deck.draw())

    for _ <- 0 until numToDraw do player.addCard(deck.draw())

    Gui.updateGui()
