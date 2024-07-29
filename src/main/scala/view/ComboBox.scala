package view

import java.awt.{Color, Dimension}
import javax.swing.{DefaultComboBoxModel, JComboBox}

class ComboBox(items: Array[String]) extends JComboBox:

  def this(items: Array[String], size: (Int, Int)) =
    this(items)
    setPreferredSize(new Dimension(size(0), size(1)))
    setMaximumSize(new Dimension(size(0), size(1)))
    setMinimumSize(new Dimension(size(0), size(1)))

  private val itemsObject: Array[Object] = items.asInstanceOf[Array[Object]]
  setModel(new DefaultComboBoxModel(itemsObject))
  setFont(getFont.deriveFont(20f))
  setBackground(Color.WHITE)
  setForeground(Color.BLACK)

