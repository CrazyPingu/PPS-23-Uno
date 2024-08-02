import controller.{GameLoop, PageController}
import model.Player
import model.cards.SpecialCard
import view.game.GameGui

object Main:

  def main(args: Array[String]): Unit =
    val player: Player = Player()
    val gameGui: GameGui = GameGui()
    val gameLoop: GameLoop = GameLoop(player, gameGui)
    SpecialCard.setGameLoop(gameLoop)
    GameGui.gameLoop = gameLoop
    val pageController: PageController = PageController(player, gameGui, gameLoop)
    GameLoop.pageController = pageController
    pageController.showMainMenu()
