package view

import java.awt.{Color, Dimension}
import javax.swing.JLabel

/**
 * The Label class represents a custom JLabel with specific configurations for displaying text.
 *
 * @param text The text to be displayed in the label.
 */
class Label(private val text: String) extends JLabel:

  /**
   * Creates a Label with a specific size.
   *
   * @param text       The text to be displayed in the label.
   * @param buttonSize The size of the label.
   */
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
