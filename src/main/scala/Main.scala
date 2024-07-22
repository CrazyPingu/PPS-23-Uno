import controller.PageController
import view.mainmenu.Mainmenu
import view.{CardLayoutId, Frame}

object Main:

  def main(args: Array[String]): Unit =
    val frame = new Frame()
    val pageController = new PageController(frame)

    val mainMenu = new Mainmenu(pageController)
    frame.add(mainMenu, CardLayoutId.MainMenu)

    pageController.showMainMenu()
