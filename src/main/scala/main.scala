import controller.{Controller, GameLoop}
import model.bot.EasyBotPlayerImpl
import model.{Deck, Hand}
import view.game.Gui
import view.{CardLayoutId, Frame}

object main:

  def main(args: Array[String]): Unit =

    val controller = new Controller()
    val gui = new Gui(controller)
    controller.setGui(gui)
    val frame = new Frame()
    frame.add(gui, CardLayoutId.Game)
    frame.show(CardLayoutId.Game)

    new GameLoop(controller, gui).start()
