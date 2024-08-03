package view.game.ChangeColor

import controller.GameLoop
import utils.Color

import javax.swing.JButton

/**
 * A button that allows the player to choose a color
 *
 * @param color the color of the button
 * @param gameLoop the game loop
 */
class ColorButton(private val color: Color, private val gameLoop: GameLoop) extends JButton:
  setBorderPainted(false)
  setBackground(new java.awt.Color(color.rgb))

  addActionListener(
    _ => gameLoop.changeColor(color)
  )
