package view.game.ChangeColor

import controller.GameController
import utils.Color

import java.awt.GridLayout
import javax.swing.JPanel

object ChooseColor extends JPanel:
  this.setLayout(new GridLayout(2, 2))

  this.add(new ColorButton(Color.Red))
  this.add(new ColorButton(Color.Blue))
  this.add(new ColorButton(Color.Green))
  this.add(new ColorButton(Color.Yellow))
