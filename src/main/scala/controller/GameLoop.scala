package controller

import controller.GameLoop.{
  clockWiseDirection,
  createBotPlayers,
  currentPlayer,
  currentTurn,
  deck,
  giveStartingCards,
  isRunning,
  lastPlayedCard,
  pageController,
  turnOrder,
  unoCalled
}
import model.achievements.{AchievementId, Event}
import model.bot.{BotPlayer, EasyBotPlayerImpl, HardBotPlayerImpl}
import model.cards.SpecialCard.{ChangeColor, WildDrawFourCard}
import model.cards.{Card, SimpleCard, SpecialCard}
import model.settings.Difficulty.Difficulty
import model.settings.{Difficulty, Settings}
import model.{Deck, Hand, Player}
import utils.Color
import utils.Compatibility.isCompatible
import view.game.GameGui

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Random

/**
 * A class representing the main game loop.
 *
 * @param player  The player of the game.
 * @param gameGui The GUI of the game.
 */
case class GameLoop private (private val player: Player, private val gameGui: GameGui):

  /**
   * Start the game loop.
   */
  def start(): Unit =
    player.clearHand()

    val currentSettings: Settings = SettingsController.getCurrentSettings
    val (bot1, bot2, bot3) = createBotPlayers(currentSettings.difficulty)

    currentTurn = 0
    clockWiseDirection = true
    isRunning = true
    unoCalled = false
    turnOrder = List(player, bot1, bot2, bot3)

    gameGui.setEntity(bot1, bot2, bot3, player)

    disposeCard(deck.draw())

    gameGui.setUnoButtonChecked(false)

    giveStartingCards(bot1, bot2, bot3, player, currentSettings.startCardValue, currentSettings.handicap)
    gameGui.updateTurnArrow(currentTurn)
    gameGui.updateGui()

  /**
   * Proceed to the next turn.
   */
  private def nextTurn(): Unit =
    if !isRunning then return
    Future:
      currentTurn = (currentTurn + (if clockWiseDirection then 1 else -1) + turnOrder.size) % turnOrder.size
      gameGui.updateTurnArrow(currentTurn)
      turnOrder(currentTurn) match
        case bot: BotPlayer =>
          gameGui.allowPlayerAction(false)
          Thread.sleep((1500 + Random.nextInt(1500)).toLong)
          bot.chooseCardToUse(lastPlayedCard) match
            case Some(card) => chooseCard(card, bot)
            case None       => drawCard(bot)
        case _ =>
          AchievementController.notifyAchievements(Event(AchievementId.Hold2CardsAchievement.id, player.getCardCount))
          checkUno()
          gameGui.allowPlayerAction(true)

  /**
   * Reverse the turn order.
   */
  def reverseTurnOrder(): Unit =
    clockWiseDirection = !clockWiseDirection
    gameGui.reverseDirection()

  /**
   * Proceed to make the next player draw some cards.
   *
   * @param numberToDraw The number of cards to draw.
   */
  def nextDrawCard(numberToDraw: Int): Unit =
    val nextPlayer = turnOrder((currentTurn + (if clockWiseDirection then 1 else -1) + turnOrder.size) % turnOrder.size)
    drawCard(nextPlayer, numberToDraw, false)
    if lastPlayedCard.color == Color.Black then showChangeColor()

  /**
   * Proceed to make skip the next player's turn.
   *
   * @param numberToSkip The number of turns to skip.
   */
  def skipNextTurn(numberToSkip: Int): Unit =
    if clockWiseDirection then currentTurn = (currentTurn + numberToSkip + turnOrder.size) % turnOrder.size
    else currentTurn = (currentTurn - numberToSkip - turnOrder.size + turnOrder.size) % turnOrder.size

  /**
   * Stop the game loop.
   */
  def stop(): Unit = isRunning = false

  /**
   * Draw a card for the player.
   *
   * @param hand The hand to draw the card for. Default is the player.
   * @param num The number of cards to draw. Default is 1.
   * @param skipTurn Whether to skip the turn after drawing the card. Default is true.
   */
  def drawCard(hand: Hand = player, num: Int = 1, skipTurn: Boolean = true): Unit =
    if deck.isEmpty then deck = new Deck
    for _ <- 0 until num do hand.addCard(deck.draw())
    gameGui.updateGui()
    if skipTurn then nextTurn()

  /**
   * Check if the player can play the card. If so, play the card.
   *
   * @param card The card to play.
   * @param hand The hand to play the card from. Default is the player.
   */
  def chooseCard(card: Card, hand: Hand = player): Unit =
    if isCompatible(card, lastPlayedCard) then
      disposeCard(card)
      hand.removeCard(card)
      if hand == player then
        AchievementController.notifyAchievements(Event(AchievementId.FirstCardAchievement.id, true))

      checkIfSpecialCard(card, hand == player)

      if player.isEmpty then
        AchievementController.notifyAchievements(Event(AchievementId.FirstWinAchievement.id, true))
        AchievementController.saveAchievements()
        pageController.showWin()
      else if hand.isEmpty && hand != player then
        AchievementController.notifyAchievements(Event(AchievementId.FirstLoseAchievement.id, true))
        AchievementController.saveAchievements()
        pageController.showLose()

      if card.color != Color.Black then
        gameGui.updateGui()
        nextTurn()

  /**
   * Show the change color screen to the player.
   * If the current player is a bot, the bot will choose a color automatically
   */
  def showChangeColor(): Unit =
    currentPlayer match
      case _: BotPlayer => changeColor(currentPlayer.asInstanceOf[BotPlayer].chooseColor())
      case _            => pageController.showChangeColor()

  /**
   * Change the color of the last played card
   * If the last played card is a DrawCard, the next card will have to play the new color
   *
   * @param color The new color chosen by the player
   */
  def changeColor(color: Color): Unit =
    lastPlayedCard match
      case _: ChangeColor      => lastPlayedCard = ChangeColor(color)
      case _: WildDrawFourCard => lastPlayedCard = WildDrawFourCard(color)
      case c: SimpleCard       => lastPlayedCard = SimpleCard(c.num, color)
      case _                   =>
    disposeCard(lastPlayedCard)
    pageController.showGame(false)
    nextTurn()

  /**
   * Call uno if the player has only one card left
   */
  def callUno(): Unit =
    if player.getCardCount == 1 then
      unoCalled = true
      gameGui.setUnoButtonChecked(true)

  /**
   * Check if the player has called uno
   */
  private def checkUno(): Unit =
    if !unoCalled && player.getCardCount == 1 then unoNotCalled()
    unoCalled = false
    gameGui.setUnoButtonChecked(false)

  /**
   * Method called when the player has not called uno
   */
  private def unoNotCalled(): Unit = drawCard(player, 1, false)

  /**
   * Check if the card is a special card and execute it
   *
   * @param card The card to check
   * @param isPlayer Whether the card is played by the player
   */
  private def checkIfSpecialCard(card: Card, isPlayer: Boolean = false): Unit =
    gameGui.updateGui()
    card match
      case c: WildDrawFourCard if c.color == Color.Black && isPlayer =>
        AchievementController.notifyAchievements(Event(AchievementId.FirstPlus4Achievement.id, true))
        c.execute()
      case c: ChangeColor if c.color == Color.Black && isPlayer =>
        AchievementController.notifyAchievements(Event(AchievementId.FirstColorChangeAchievement.id, true))
        c.execute()
      case c: SpecialCard => c.execute()
      case _              => ()

  /**
   * Dispose the card and update the GUI
   *
   * @param card The card to dispose
   */
  private def disposeCard(card: Card): Unit =
    lastPlayedCard = card
    gameGui.disposeCard(card)

/**
 * The companion object of the GameLoop class.
 */
object GameLoop:
  private var turnOrder: List[Hand] = _
  private var currentTurn: Int = 0
  private var clockWiseDirection: Boolean = true
  private var isRunning: Boolean = false
  private var unoCalled: Boolean = false
  private var lastPlayedCard: Card = _
  private var deck: Deck = new Deck
  var pageController: PageController = _

  /**
   * Give starting cards to all players
   */
  private def giveStartingCards(
    bot1: BotPlayer,
    bot2: BotPlayer,
    bot3: BotPlayer,
    player: Player,
    numToDraw: Int = 7,
    handicap: Int = 0
  ): Unit =
    for _ <- 0 until numToDraw + handicap do
      bot1.addCard(deck.draw())
      bot2.addCard(deck.draw())
      bot3.addCard(deck.draw())

    for _ <- 0 until numToDraw do player.addCard(deck.draw())

  /**
   * Create bot players based on the difficulty level
   *
   * @param difficulty The difficulty level of the bots
   * @return The bot players
   */
  private def createBotPlayers(difficulty: Difficulty): (BotPlayer, BotPlayer, BotPlayer) = difficulty match
    case Difficulty.Easy => (new EasyBotPlayerImpl, new EasyBotPlayerImpl, new EasyBotPlayerImpl)
    case Difficulty.Hard => (new HardBotPlayerImpl, new HardBotPlayerImpl, new HardBotPlayerImpl)
    case _ =>
      throw new IllegalArgumentException("Unsupported difficulty level")

  /**
   * Get the current player
   *
   * @return The current player
   */
  private def currentPlayer: Hand = turnOrder(currentTurn)

  /**
   * Create a new game loop
   *
   * @param player  The player of the game.
   * @param gameGui The GUI of the game.
   * @return The game loop
   */
  def apply(player: Player, gameGui: GameGui): GameLoop = new GameLoop(player, gameGui)
