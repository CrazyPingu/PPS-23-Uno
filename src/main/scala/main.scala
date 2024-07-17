import controller.{Controller, GameLoop}
import model.bot.EasyBotPlayerImpl
import model.{Deck, Hand}
import view.CardLayoutId.Settings
import view.game.Gui
import view.settings.SettingsGui
import view.{CardLayoutId, Frame}

object main:

  def main(args: Array[String]): Unit =

    val controller = new Controller()
    val gui = new Gui(controller)
    controller.setGui(gui)

    val settingsGui = new SettingsGui()

    val frame = new Frame()
    frame.add(gui, CardLayoutId.Game)
    frame.add(settingsGui, CardLayoutId.Settings)

    frame.show(CardLayoutId.Game)
    //frame.show(CardLayoutId.Settings)

    new GameLoop(controller, gui).start()
