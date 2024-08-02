package view.game.ChangeColor

import controller.GameLoop
import utils.Color

import java.awt.GridLayout
import javax.swing.JPanel

/**
 * The panel that allows the player to choose a color
 *
 * @param gameLoop the game loop
 */
class ChooseColor private (private val gameLoop: GameLoop) extends JPanel:
  this.setLayout(new GridLayout(2, 2))

  this.add(new ColorButton(Color.Red, gameLoop))
  this.add(new ColorButton(Color.Blue, gameLoop))
  this.add(new ColorButton(Color.Green, gameLoop))
  this.add(new ColorButton(Color.Yellow, gameLoop))

/**
 * Companion object of the ChooseColor class
 */
object ChooseColor:

  /**
   * Create a new ChooseColor panel
   *
   * @param gameLoop the game loop
   * @return a new ChooseColor panel
   */
  def apply(gameLoop: GameLoop): ChooseColor = new ChooseColor(gameLoop)
