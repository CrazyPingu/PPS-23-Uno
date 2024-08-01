package view.game.ChangeColor

import controller.GameLoop
import utils.Color

import javax.swing.JButton

class ColorButton(private val color: Color, private val gameLoop: GameLoop) extends JButton:
  setBorderPainted(false)

  color match
    case Color.Red    => setBackground(new java.awt.Color(color.rgb))
    case Color.Blue   => setBackground(new java.awt.Color(color.rgb))
    case Color.Green  => setBackground(new java.awt.Color(color.rgb))
    case Color.Yellow => setBackground(new java.awt.Color(color.rgb))
    case _            => ()

  addActionListener(
    _ => gameLoop.changeColor(color)
  )
