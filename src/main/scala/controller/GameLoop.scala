package controller

import model.bot.EasyBotPlayerImpl
import model.{Deck, Hand}
import view.game.Gui

class GameLoop(controller: Controller, gui: Gui):
  private val deck: Deck = Deck()
  private val pHand: Hand = Hand()
  private val bot1 = new EasyBotPlayerImpl
  private val bot2 = new EasyBotPlayerImpl
  private val bot3 = new EasyBotPlayerImpl
  private val directionClockwise = true

  gui.setEntity(bot1, bot2, bot3, pHand)

  controller.startNewGame(pHand, deck)

  def start(): Unit =
    println("GameLoop started")
    giveStartingCards()

  /**
   * Give starting cards to all players
   */
  private def giveStartingCards(): Unit =
    for _ <- 0 until 7 do
      bot1.addCard(deck.draw())
      bot2.addCard(deck.draw())
      bot3.addCard(deck.draw())
      pHand.addCard(deck.draw())
    gui.updateGui()
