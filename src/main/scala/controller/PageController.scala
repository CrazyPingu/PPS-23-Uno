package controller

import model.cards.factory.CardFactoryImpl
import view.CardLayoutId.{MainMenu, Settings}
import view.game.ChangeColor.ChooseColor
import view.game.Gui
import view.settings.SettingsGui
import view.{CardLayoutId, Frame, LoseScreen, WinScreen}

class PageController(private val frame: Frame):

  private val cardFactory: CardFactoryImpl = CardFactoryImpl()
  private val controller = new GameController(this, cardFactory)
  cardFactory.attachController(controller)
  private val gui = new Gui(controller)
  private val gameLoop = new GameLoop(controller, gui, cardFactory)
  controller.setGuiAndGameLoop(gui, gameLoop)

  frame.add(new WinScreen(this), CardLayoutId.Win)
  frame.add(new LoseScreen(this), CardLayoutId.Lose)
  frame.add(gui, CardLayoutId.Game)
  frame.add(new ChooseColor(controller), CardLayoutId.ChangeColor)

  private val settingsController: SettingsController = SettingsController()
  private val settingsGui: SettingsGui = SettingsGui(this, settingsController)
  frame.add(settingsGui, CardLayoutId.Settings)

  def showMainMenu(): Unit =
    frame.show(MainMenu)

  def showGame(newGame: Boolean = true): Unit =
    if newGame then gameLoop.start()
    frame.show(CardLayoutId.Game)

  def showChangeColor(): Unit =
    frame.show(CardLayoutId.ChangeColor)

  def showTutorial(): Unit =
    frame.show(CardLayoutId.Tutorial)

  def showAchievements(): Unit =
    frame.show(CardLayoutId.Achievement)

  def showSettings(): Unit =
    frame.show(CardLayoutId.Settings)

  def showWin(): Unit =
    gameLoop.stop()
    frame.show(CardLayoutId.Win)

  def showLose(): Unit =
    gameLoop.stop()
    frame.show(CardLayoutId.Lose)

  def closeWindow(): Unit =
    frame.dispose()
