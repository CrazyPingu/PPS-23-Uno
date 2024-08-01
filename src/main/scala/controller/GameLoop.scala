package controller

import model.bot.{BotPlayer, EasyBotPlayerImpl, HardBotPlayerImpl}
import model.cards.Card
import model.settings.Difficulty.Difficulty
import model.settings.{Difficulty, GameSettings}
import model.{Deck, Hand, Player}
import view.game.GameGui

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Random

object GameLoop:
  private var bot1: BotPlayer = _
  private var bot2: BotPlayer = _
  private var bot3: BotPlayer = _
  private var turnOrder: List[Hand] = _
  private var currentTurn: Int = 0
  private var clockWiseDirection: Boolean = true
  private var isRunning: Boolean = false

  def start(): Unit =
    val currentSettings: GameSettings = SettingsController.settings.gameSettings
    Player.clearHand()

    val bots = createBotPlayers(currentSettings.difficulty)
    bot1 = bots(0)
    bot2 = bots(1)
    bot3 = bots(2)
    currentTurn = 0
    clockWiseDirection = true
    isRunning = true
    turnOrder = List(Player, bot1, bot2, bot3)

    GameGui.setEntity(bot1, bot2, bot3)
    GameController.startNewGame()

    giveStartingCards(bot1, bot2, bot3, currentSettings.startCardValue, currentSettings.handicap)
    GameGui.updateTurnArrow(currentTurn)

  def nextTurn(): Unit =
    if !isRunning then return
    Future:
      currentTurn = (currentTurn + (if clockWiseDirection then 1 else -1) + turnOrder.size) % turnOrder.size
      GameGui.updateTurnArrow(currentTurn)
      turnOrder(currentTurn) match
        case bot: BotPlayer =>
          Thread.sleep((1500 + Random.nextInt(1500)).toLong)
          bot.chooseCardToUse(GameController.lastPlayedCard) match
            case Some(card) =>
              GameController.chooseCard(card, bot)
            case None =>
              GameController.drawCard(bot)
        case _ =>
          GameController.checkUno()
          GameGui.allowPlayerAction(true)

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
    numToDraw: Int = 7,
    handicap: Int = 0
  ): Unit =
    for _ <- 0 until numToDraw + handicap do
      bot1.addCard(Deck.draw())
      bot2.addCard(Deck.draw())
      bot3.addCard(Deck.draw())

    for _ <- 0 until numToDraw do Player.addCard(Deck.draw())

    GameGui.updateGui()
