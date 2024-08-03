import controller.{GameLoop, PageController}
import model.Player
import model.cards.SpecialCard
import view.game.GameGui

/**
 * Singleton object that contains the main method of the game.
 */
object Main:

  /**
   * Main method of the game.
   *
   * @param args Command line arguments.
   */
  def main(args: Array[String]): Unit =
    val player: Player = Player()
    val gameGui: GameGui = GameGui()
    val gameLoop: GameLoop = GameLoop(player, gameGui)
    SpecialCard.setGameLoop(gameLoop)
    GameGui.gameLoop = gameLoop
    val pageController: PageController = PageController(gameGui, gameLoop)
    GameLoop.pageController = pageController
    pageController.showMainMenu()
