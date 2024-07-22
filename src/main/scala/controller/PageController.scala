package controller

import model.cards.factory.CardFactoryImpl
import view.CardLayoutId.MainMenu
import view.game.ChangeColor.ChooseColor
import view.game.Gui
import view.{CardLayoutId, Frame}

class PageController(frame: Frame):

  private val cardFactory: CardFactoryImpl = CardFactoryImpl()
  private val controller = new GameController(this, cardFactory)
  cardFactory.attachController(controller)
  private val gui = new Gui(controller)
  private val gameLoop = new GameLoop(controller, gui, cardFactory)
  controller.setGuiAndGameLoop(gui, gameLoop)
  frame.add(gui, CardLayoutId.Game)
  frame.add(new ChooseColor(controller), CardLayoutId.ChangeColor)

  def showMainMenu(): Unit =
    frame.show(MainMenu)

  def showGame(newGame: Boolean = true): Unit =
    frame.show(CardLayoutId.Game)
    if newGame then gameLoop.start()

  def showChangeColor(): Unit =
    frame.show(CardLayoutId.ChangeColor)

  def showTutorial(): Unit =
    frame.show(CardLayoutId.Tutorial)

  def showAchievements(): Unit =
    frame.show(CardLayoutId.Achievement)

  def showSettings(): Unit =
    frame.show(CardLayoutId.Settings)

  def showWin(): Unit =
    frame.show(CardLayoutId.Win)

  def showLose(): Unit =
    frame.show(CardLayoutId.Lose)

  def closeWindow(): Unit =
    frame.dispose()