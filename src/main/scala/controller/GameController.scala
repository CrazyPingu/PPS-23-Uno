package controller

import model.achievements.{AchievementId, Event}
import model.bot.BotPlayer
import model.cards.factory.CardFactory
import model.cards.special.{ChangeColor, DrawCard}
import model.cards.{Card, SpecialCard}
import model.{Deck, Hand}
import utils.Color
import utils.Compatibility.isCompatible
import view.game.Gui

object GameController:
  private var deck: Option[Deck] = None
  private var playerHand: Option[Hand] = None
  var lastPlayedCard: Option[Card] = None
  private var unoCalled = false

  def drawCard(hand: Hand = playerHand.get, num: Int = 1, skipTurn: Boolean = true): Unit =
    if deck.get.isEmpty then deck = Some(Deck())
    Gui.allowPlayerAction(false)
    for _ <- 0 until num do hand.addCard(deck.get.draw())
    AchievementController.notifyAchievements(Event(AchievementId.hold2CardsAchievement.value, playerHand.get.length))
    Gui.updateGui()
    if skipTurn then GameLoop.nextTurn()

  def startNewGame(playerHand: Hand, deck: Deck): Unit =
    this.playerHand = Some(playerHand)
    this.deck = Some(deck)
    lastPlayedCard = Some(deck.draw())
    Gui.disposeCard(this.lastPlayedCard.get)
    this.unoCalled = false
    Gui.setUnoButtonChecked(false)

  def chooseCard(card: Card, hand: Hand = playerHand.get): Unit =
    if isCompatible(card, lastPlayedCard.get) then
      Gui.allowPlayerAction(false)
      disposeCard(card)
      hand.removeCard(card)
      if hand == playerHand.get then
        AchievementController.notifyAchievements(Event(AchievementId.firstCardAchievement.value, 1))
      checkIfSpecialCard(card, hand == playerHand.get)

      if playerHand.get.isEmpty then
        AchievementController.notifyAchievements(Event(AchievementId.firstWinAchievement.value, 1))
        AchievementController.saveAchievements()
        PageController.showWin()
      else if hand.isEmpty then
        AchievementController.notifyAchievements(Event(AchievementId.firstLoseAchievement.value, 1))
        AchievementController.saveAchievements()
        PageController.showLose()

      if card.color != Color.Black then
        Gui.updateGui()
        GameLoop.nextTurn()
    else println("Card not compatible " + card + "\nLast played card " + lastPlayedCard.get)

  def reverseDirection(): Unit =
    GameLoop.reverseTurnOrder()
    Gui.reverseDirection()

  def nextDrawCard(numberToDraw: Int): Unit =
    GameLoop.nextDrawCard(numberToDraw)
    if lastPlayedCard.get.color == Color.Black then showChangeColor()

  def skipNextTurn(numberToSkip: Int): Unit =
    GameLoop.skipNextTurn(numberToSkip)

  def showChangeColor(): Unit =
    if GameLoop.currentPlayer == playerHand.get then PageController.showChangeColor()
    else
      val color = GameLoop.currentPlayer.asInstanceOf[BotPlayer].chooseColor()
      changeColor(color)

  /**
   * Change the color of the last played card
   * If the last played card is a DrawCard, the next card will have to play the new color
   *
   * @param color The new color chosen by the player
   */
  def changeColor(color: Color): Unit =
    lastPlayedCard.get match
      case _: ChangeColor => lastPlayedCard = Some(CardFactory.createChangeColor(color))
      case _: DrawCard    => lastPlayedCard = Some(CardFactory.createDrawCard(4, color))
      case _              =>

    disposeCard(lastPlayedCard.get)
    PageController.showGame(false)
    GameLoop.nextTurn()

  def callUno(): Unit =
    if playerHand.get.size == 1 then
      unoCalled = true
      Gui.setUnoButtonChecked(true)

  def checkUno(): Unit =
    if !unoCalled && playerHand.get.size == 1 then
      println("You didn't call UNO!")
      drawCard(playerHand.get, 1, false)
    else if unoCalled then println("Called UNO correctly!")
    unoCalled = false
    Gui.setUnoButtonChecked(false)

  private def checkIfSpecialCard(card: Card, isPlayer: Boolean = false): Unit =
    card match
      case c: DrawCard if c.numberToDraw == 4 && c.color == Color.Black && isPlayer =>
        AchievementController.notifyAchievements(Event(AchievementId.firstPlus4Achievement.value, 1))
        c.execute()
      case c: ChangeColor if c.color == Color.Black && isPlayer =>
        AchievementController.notifyAchievements(Event(AchievementId.firstColorChangeAchievement.value, 1))
        c.execute()
      case c: SpecialCard => c.execute()
      case _              => ()

  private def disposeCard(card: Card): Unit =
    lastPlayedCard = Some(card)
    Gui.disposeCard(card)
//    Gui.updateGui()
