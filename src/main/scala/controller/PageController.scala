package controller

import controller.PageController.frame
import model.Player
import view.Mainmenu
import view.achievements.AchievementGui
import view.game.ChangeColor.ChooseColor
import view.game.GameGui
import view.settings.SettingsGui
import view.tutorial.TutorialGui
import view.{CardLayoutId, Frame, LoseScreen, WinScreen}

/**
 * Controller for the pages of the game
 *
 * @param player the player of the game
 * @param gameGui the game gui, which is the main game view
 * @param gameLoop the game loop that controls the game
 */
case class PageController private (player: Player, gameGui: GameGui, gameLoop: GameLoop):

  private val achievementGui: AchievementGui = AchievementGui(this)
  frame.add(Mainmenu(this), CardLayoutId.MainMenu)
  frame.add(WinScreen(this), CardLayoutId.Win)
  frame.add(LoseScreen(this), CardLayoutId.Lose)
  frame.add(gameGui, CardLayoutId.Game)
  frame.add(ChooseColor(gameLoop), CardLayoutId.ChangeColor)
  frame.add(SettingsGui(this), CardLayoutId.Settings)
  frame.add(achievementGui, CardLayoutId.Achievement)
  frame.add(TutorialGui(this), CardLayoutId.Tutorial)

  /**
   * Shows the main menu where the player can start a new game
   */
  def showMainMenu(): Unit =
    frame.show(CardLayoutId.MainMenu)

  /**
   * Shows and start the game
   *
   * @param newGame if true, start a new game. Default is true
   */
  def showGame(newGame: Boolean = true): Unit =
    if newGame then gameLoop.start()
    frame.show(CardLayoutId.Game)

  /**
   * Shows the screen where the player can choose a color
   */
  def showChangeColor(): Unit =
    frame.show(CardLayoutId.ChangeColor)

  /**
   * Shows the tutorial screen
   */
  def showTutorial(): Unit =
    frame.show(CardLayoutId.Tutorial)

  /**
   * Shows the achievements screen
   */
  def showAchievements(): Unit =
    achievementGui.updateGui()
    frame.show(CardLayoutId.Achievement)

  /**
   * Shows the settings page
   */
  def showSettings(): Unit =
    frame.show(CardLayoutId.Settings)

  /**
   * Shows the win screen
   */
  def showWin(): Unit =
    gameLoop.stop()
    frame.show(CardLayoutId.Win)

  /**
   * Shows the lose screen
   */
  def showLose(): Unit =
    gameLoop.stop()
    frame.show(CardLayoutId.Lose)

  /**
   * Closes the window
   */
  def closeWindow(): Unit =
    frame.dispose()

/**
 * Companion object for the PageController class
 */
object PageController:

  private val frame: Frame = Frame()

  /**
   * Creates a new PageController instance
   *
   * @param player the player of the game
   * @param gameGui the game gui, which is the main game view
   * @param gameLoop the game loop that controls the game
   * @return a new PageController instance
   */
  def apply(player: Player, gameGui: GameGui, gameLoop: GameLoop): PageController =
    new PageController(player, gameGui, gameLoop)
