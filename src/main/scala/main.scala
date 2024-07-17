import controller.{Controller, GameLoop}
import model.bot.EasyBotPlayerImpl
import model.cards.SimpleCardImpl
import model.cards.factory.CardFactoryImpl
import model.{Deck, Hand}
import utils.Color
import utils.Compatibility.isCompatible
import view.game.Gui
import view.{CardLayoutId, Frame}

object main:

  def main(args: Array[String]): Unit =
    val controller = new Controller()
    val gui = new Gui(controller)
    val gameLoop = new GameLoop(controller, gui)
    controller.setGuiAndGameLoop(gui, gameLoop)
    val frame = new Frame()
    frame.add(gui, CardLayoutId.Game)
    frame.show(CardLayoutId.Game)

    gameLoop.start()
