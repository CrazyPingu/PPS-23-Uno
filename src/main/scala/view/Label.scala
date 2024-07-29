package view

import java.awt.{Color, Dimension}
import javax.swing.JLabel

class Label(text: String) extends JLabel:

  def this(text: String, buttonSize: (Int, Int)) =
    this(text)
    setPreferredSize(new Dimension(buttonSize(0), buttonSize(1)))
    setMaximumSize(new Dimension(buttonSize(0), buttonSize(1)))
    setMinimumSize(new Dimension(buttonSize(0), buttonSize(1)))

  setText(text)
  setFocusable(false)
  setFont(getFont.deriveFont(20f))
  setBackground(Color.WHITE)
  setForeground(Color.WHITE)
