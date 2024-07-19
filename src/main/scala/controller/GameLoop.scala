package controller

import model.bot.{BotPlayer, EasyBotPlayerImpl}
import model.cards.Card
import model.cards.factory.CardFactoryImpl
import model.{Deck, Hand}
import utils.Color.Red
import view.game.Gui

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class GameLoop(private val controller: Controller, private val gui: Gui):
  private val player: Hand = Hand()
  private val (bot1, bot2, bot3) = createBotPlayers()
  private var turnOrder = List(player, bot1, bot2, bot3)
  private var currentTurn = 0
  private var clockWiseDirection = true

  def start(): Unit =
    println("GameLoop started")
    val deck = Deck(controller)
    currentTurn = 0

//    var directionClockwise = true

    gui.setEntity(bot1, bot2, bot3, player)
    controller.startNewGame(player, deck)

    giveStartingCards(bot1, bot2, bot3, player, deck)

  def nextTurn(): Unit =
    Future:
      Thread.sleep(1000)
      currentTurn = (currentTurn + (if clockWiseDirection then 1 else -1) + turnOrder.size) % turnOrder.size
      turnOrder(currentTurn) match
        case bot: BotPlayer =>
          bot.chooseCardToUse(controller.lastPlayedCard.get) match
            case Some(card) =>
              println("Bot" + currentTurn + " chose " + card)
              controller.chooseCard(card, bot)
            case None =>
              controller.drawCard(bot)
        case _ =>
          gui.allowPlayerAction(true)
          println("Player's turn")

  def reverseTurnOrder(): Unit = clockWiseDirection = !clockWiseDirection

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

    player.addCard(new CardFactoryImpl(controller).createDrawCard(2, Red))
    gui.updateGui()
    println("Bot1 " + bot1)
    println("Bot2 " + bot2)
    println("Bot3 " + bot3)
