package view.game.ChangeColor

import controller.GameController
import utils.Color

import javax.swing.JButton

class ColorButton(color: Color, controller: GameController) extends JButton:
  setBorderPainted(false)

  color match
    case Color.Red    => setBackground(new java.awt.Color(color.rgb))
    case Color.Blue   => setBackground(new java.awt.Color(color.rgb))
    case Color.Green  => setBackground(new java.awt.Color(color.rgb))
    case Color.Yellow => setBackground(new java.awt.Color(color.rgb))
    case _            => ()

  addActionListener(
    _ => controller.changeColor(color)
  )
