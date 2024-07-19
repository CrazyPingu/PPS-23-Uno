import controller.{GameController, GameLoop}
import model.cards.factory.CardFactoryImpl
import view.game.ChangeColor.ChooseColor
import view.game.Gui
import view.{CardLayoutId, Frame}

object main:

  def main(args: Array[String]): Unit =
    val frame = new Frame()
    val cardFactory: CardFactoryImpl = CardFactoryImpl()
    val controller = new GameController(frame, cardFactory)
    cardFactory.attachController(controller)
    val gui = new Gui(controller)
    val gameLoop = new GameLoop(controller, gui, cardFactory)
    controller.setGuiAndGameLoop(gui, gameLoop)

    frame.add(new ChooseColor(controller), CardLayoutId.ChangeColor)

    frame.add(gui, CardLayoutId.Game)
    frame.show(CardLayoutId.Game)

    gameLoop.start()
