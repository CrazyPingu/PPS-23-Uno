package view

import java.awt.{Color, Dimension}
import javax.swing.JButton

class Button(text: String) extends JButton:

  def this(text: String, buttonSize: (Int, Int)) =
    this(text)
    setPreferredSize(new Dimension(buttonSize(0), buttonSize(1)))
    setMaximumSize(new Dimension(buttonSize(0), buttonSize(1)))
    setMinimumSize(new Dimension(buttonSize(0), buttonSize(1)))

  setText(text)
  setFocusable(false)
  setFont(getFont.deriveFont(20f))
  setBorderPainted(false)
  setBackground(Color.WHITE)
  setForeground(Color.BLACK)

  addMouseListener(new java.awt.event.MouseAdapter:
    override def mouseEntered(e: java.awt.event.MouseEvent): Unit =
      setBackground(Color.LIGHT_GRAY)
    override def mouseExited(e: java.awt.event.MouseEvent): Unit =
      setBackground(Color.WHITE)
  )