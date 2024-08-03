package view

import java.awt.{Color, Dimension}
import javax.swing.{DefaultComboBoxModel, JComboBox}

/**
 * The ComboBox class represents a custom JComboBox with specific configurations for displaying items.
 *
 * @param items The items to be displayed in the combo box.
 */
class ComboBox(private val items: Array[String]) extends JComboBox:

  /**
   * Creates a ComboBox with a specific size.
   *
   * @param items The items to be displayed in the combo box.
   * @param size  The size of the combo box.
   */
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
