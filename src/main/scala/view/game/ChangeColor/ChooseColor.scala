package view.game.ChangeColor

import utils.Color

import java.awt.GridLayout
import javax.swing.JPanel

class ChooseColor private extends JPanel:
  this.setLayout(new GridLayout(2, 2))

  this.add(new ColorButton(Color.Red))
  this.add(new ColorButton(Color.Blue))
  this.add(new ColorButton(Color.Green))
  this.add(new ColorButton(Color.Yellow))

object ChooseColor:
  def apply(): ChooseColor = new ChooseColor()
