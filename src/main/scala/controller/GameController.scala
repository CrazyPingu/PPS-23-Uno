package controller

import model.achievements.{AchievementId, Event}
import model.bot.BotPlayer
import model.cards.factory.CardFactory
import model.cards.special.{ChangeColor, DrawCard}
import model.cards.{Card, SpecialCard}
import model.{Deck, Hand, Player}
import utils.Color
import utils.Compatibility.isCompatible
import view.game.GameGui

object GameController:
  private var unoCalled = false
  var lastPlayedCard: Card = _

  startNewGame()

  def startNewGame(): Unit =
    lastPlayedCard = Deck.draw()
    GameGui.disposeCard(this.lastPlayedCard)
    this.unoCalled = false
    GameGui.setUnoButtonChecked(false)

  def drawCard(hand: Hand = Player, num: Int = 1, skipTurn: Boolean = true): Unit =
    if Deck.isEmpty then Deck.initialize()
    GameGui.allowPlayerAction(false)
    for _ <- 0 until num do hand.addCard(Deck.draw())
    AchievementController.notifyAchievements(Event(AchievementId.hold2CardsAchievement.value, Player.getCardCount))
    GameGui.updateGui()
    if skipTurn then GameLoop.nextTurn()

  def chooseCard(card: Card, hand: Hand = Player): Unit =
    if isCompatible(card, lastPlayedCard) then
      GameGui.allowPlayerAction(false)
      disposeCard(card)
      hand.removeCard(card)
      if hand == Player then
        AchievementController.notifyAchievements(Event(AchievementId.firstCardAchievement.value, 1))
      checkIfSpecialCard(card, hand == Player)

      if Player.isEmpty && unoCalled then
        AchievementController.notifyAchievements(Event(AchievementId.firstWinAchievement.value, 1))
        AchievementController.saveAchievements()
        PageController.showWin()
      else if hand.isEmpty then
        AchievementController.notifyAchievements(Event(AchievementId.firstLoseAchievement.value, 1))
        AchievementController.saveAchievements()
        PageController.showLose()

      if card.color != Color.Black then
        GameGui.updateGui()
        GameLoop.nextTurn()

  def reverseDirection(): Unit =
    GameLoop.reverseTurnOrder()
    GameGui.reverseDirection()

  def nextDrawCard(numberToDraw: Int): Unit =
    GameLoop.nextDrawCard(numberToDraw)
    if lastPlayedCard.color == Color.Black then showChangeColor()

  def skipNextTurn(numberToSkip: Int): Unit =
    GameLoop.skipNextTurn(numberToSkip)

  def showChangeColor(): Unit =
    if GameLoop.currentPlayer == Player then PageController.showChangeColor()
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
    lastPlayedCard match
      case _: ChangeColor => lastPlayedCard = CardFactory.createChangeColor(color)
      case _: DrawCard    => lastPlayedCard = CardFactory.createDrawCard(4, color)
      case _              =>

    disposeCard(lastPlayedCard)
    PageController.showGame(false)
    GameLoop.nextTurn()

  def callUno(): Unit =
    if Player.getCardCount == 1 then
      unoCalled = true
      GameGui.setUnoButtonChecked(true)

  def checkUno(): Unit =
    if !unoCalled && Player.getCardCount == 1 then
      println("You didn't call UNO!")
      drawCard(Player, 1, false)
    else if unoCalled then println("Called UNO correctly!")
    unoCalled = false
    GameGui.setUnoButtonChecked(false)

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
    lastPlayedCard = card
    GameGui.disposeCard(card)
