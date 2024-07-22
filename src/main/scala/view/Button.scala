package view

import java.awt.{Color, Dimension}
import javax.swing.JButton

class Button(text: String) extends JButton:

  def this(text: String, buttonSize: (Int, Int)) =
    this(text)
    setPreferredSize(new Dimension(buttonSize._1, buttonSize._2))
    setMaximumSize(new Dimension(buttonSize._1, buttonSize._2))
    setMinimumSize(new Dimension(buttonSize._1, buttonSize._2))

  setText(text)
  setFocusable(false)
  setFont(getFont.deriveFont(20f))
  setBorderPainted(false)
  setBackground(Color.WHITE)
