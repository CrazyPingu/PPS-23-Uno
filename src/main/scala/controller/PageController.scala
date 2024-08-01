package controller

import view.Mainmenu
import view.achievements.AchievementGui
import view.game.ChangeColor.ChooseColor
import view.game.GameGui
import view.settings.SettingsGui
import view.tutorial.TutorialGui
import view.{CardLayoutId, Frame, LoseScreen, WinScreen}

object PageController:

  private val frame: Frame = Frame()
  private val achievementGui: AchievementGui = AchievementGui()

  frame.add(Mainmenu(), CardLayoutId.MainMenu)
  frame.add(WinScreen(), CardLayoutId.Win)
  frame.add(LoseScreen(), CardLayoutId.Lose)
  frame.add(GameGui, CardLayoutId.Game)
  frame.add(ChooseColor(), CardLayoutId.ChangeColor)
  frame.add(SettingsGui(), CardLayoutId.Settings)
  frame.add(achievementGui, CardLayoutId.Achievement)
  frame.add(TutorialGui(), CardLayoutId.Tutorial)

  def showMainMenu(): Unit =
    frame.show(CardLayoutId.MainMenu)

  def showGame(newGame: Boolean = true): Unit =
    if newGame then GameLoop.start()
    frame.show(CardLayoutId.Game)

  def showChangeColor(): Unit =
    frame.show(CardLayoutId.ChangeColor)

  def showTutorial(): Unit =
    frame.show(CardLayoutId.Tutorial)

  def showAchievements(): Unit =
    achievementGui.updateGui()
    frame.show(CardLayoutId.Achievement)

  def showSettings(): Unit =
    frame.show(CardLayoutId.Settings)

  def showWin(): Unit =
    GameLoop.stop()
    frame.show(CardLayoutId.Win)

  def showLose(): Unit =
    GameLoop.stop()
    frame.show(CardLayoutId.Lose)

  def closeWindow(): Unit =
    frame.dispose()
