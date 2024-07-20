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
  private val player: Hand = Hand()
  private val (bot1, bot2, bot3) = createBotPlayers()
  private val turnOrder = List(player, bot1, bot2, bot3)
  private var currentTurn = 0
  private var clockWiseDirection = true

  def start(): Unit =
    println("GameLoop started")
    val deck = Deck(cardFactory)
    currentTurn = 0
    clockWiseDirection = true

    gui.setEntity(bot1, bot2, bot3, player)
    controller.startNewGame(player, deck)

    giveStartingCards(bot1, bot2, bot3, player, deck)

  def nextTurn(): Unit =
    Future:
      currentTurn = (currentTurn + (if clockWiseDirection then 1 else -1) + turnOrder.size) % turnOrder.size
      turnOrder(currentTurn) match
        case bot: BotPlayer =>
          Thread.sleep((1500 + Random.nextInt(1500)).toLong)
          bot.chooseCardToUse(controller.lastPlayedCard.get) match
            case Some(card) =>
              println("Bot" + currentTurn + " chose " + card)
              controller.chooseCard(card, bot)
            case None =>
              println("Bot" + currentTurn + " drew a card")
              controller.drawCard(bot)
        case _ =>
          controller.checkUno()
          gui.allowPlayerAction(true)
          println("Player's turn")

  def reverseTurnOrder(): Unit = clockWiseDirection = !clockWiseDirection

  def currentPlayer: Hand = turnOrder(currentTurn)

  def nextDrawCard(numberToDraw: Int): Unit =
    val nextPlayer = turnOrder((currentTurn + (if clockWiseDirection then 1 else -1) + turnOrder.size) % turnOrder.size)
    controller.drawCard(nextPlayer, numberToDraw, false)

  def skipNextTurn(numberToSkip: Int): Unit =
    if clockWiseDirection then currentTurn = (currentTurn + numberToSkip + turnOrder.size) % turnOrder.size
    else currentTurn = (currentTurn - numberToSkip - turnOrder.size + turnOrder.size) % turnOrder.size

  private def createBotPlayers(): (BotPlayer, BotPlayer, BotPlayer) =
    (new EasyBotPlayerImpl, new EasyBotPlayerImpl, new EasyBotPlayerImpl)

  /**
   * Give starting cards to all players
   */
  private def giveStartingCards(bot1: BotPlayer, bot2: BotPlayer, bot3: BotPlayer, player: Hand, deck: Deck): Unit =
    for _ <- 0 until 7 do
      bot1.addCard(deck.draw())
      bot2.addCard(deck.draw())
      bot3.addCard(deck.draw())
      player.addCard(deck.draw())

    gui.updateGui()
