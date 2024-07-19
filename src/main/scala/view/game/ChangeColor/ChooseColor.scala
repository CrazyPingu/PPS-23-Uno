package view.game.ChangeColor

import controller.GameController
import utils.Color

import java.awt.GridLayout
import javax.swing.JPanel

class ChooseColor(controller: GameController) extends JPanel:
  this.setLayout(new GridLayout(2, 2))

  this.add(new ColorButton(Color.Red, controller))
  this.add(new ColorButton(Color.Blue, controller))
  this.add(new ColorButton(Color.Green, controller))
  this.add(new ColorButton(Color.Yellow, controller))
