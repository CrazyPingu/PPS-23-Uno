package controller

import view.Mainmenu
import view.achievements.AchievementGui
import view.game.ChangeColor.ChooseColor
import view.game.GameGui
import view.settings.SettingsGui
import view.tutorial.TutorialGui
import view.{CardLayoutId, Frame, LoseScreen, WinScreen}

object PageController:

  Frame.add(Mainmenu, CardLayoutId.MainMenu)
  Frame.add(WinScreen, CardLayoutId.Win)
  Frame.add(LoseScreen, CardLayoutId.Lose)
  Frame.add(GameGui, CardLayoutId.Game)
  Frame.add(ChooseColor, CardLayoutId.ChangeColor)
  Frame.add(SettingsGui, CardLayoutId.Settings)
  Frame.add(AchievementGui, CardLayoutId.Achievement)
  Frame.add(TutorialGui, CardLayoutId.Tutorial)

  def showMainMenu(): Unit =
    Frame.show(CardLayoutId.MainMenu)

  def showGame(newGame: Boolean = true): Unit =
    if newGame then GameLoop.start()
    Frame.show(CardLayoutId.Game)

  def showChangeColor(): Unit =
    Frame.show(CardLayoutId.ChangeColor)

  def showTutorial(): Unit =
    Frame.show(CardLayoutId.Tutorial)

  def showAchievements(): Unit =
    AchievementGui.updateGui()
    Frame.show(CardLayoutId.Achievement)

  def showSettings(): Unit =
    Frame.show(CardLayoutId.Settings)

  def showWin(): Unit =
    GameLoop.stop()
    Frame.show(CardLayoutId.Win)

  def showLose(): Unit =
    GameLoop.stop()
    Frame.show(CardLayoutId.Lose)

  def closeWindow(): Unit =
    Frame.dispose()
