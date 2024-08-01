package view.game.ChangeColor

import controller.GameLoop
import utils.Color

import java.awt.GridLayout
import javax.swing.JPanel

class ChooseColor private (private val gameLoop: GameLoop) extends JPanel:
  this.setLayout(new GridLayout(2, 2))

  this.add(new ColorButton(Color.Red, gameLoop))
  this.add(new ColorButton(Color.Blue, gameLoop))
  this.add(new ColorButton(Color.Green, gameLoop))
  this.add(new ColorButton(Color.Yellow, gameLoop))

object ChooseColor:
  def apply(gameLoop: GameLoop): ChooseColor = new ChooseColor(gameLoop)
