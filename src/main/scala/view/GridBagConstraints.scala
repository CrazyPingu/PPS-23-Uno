package view

import java.awt.Insets

/**
 * Represents the constraints of the grid layout.
 *
 * @param newGridx The x position of the component.
 */
class GridBagConstraints(private val newGridx: Int) extends java.awt.GridBagConstraints:

  this.gridx = newGridx

  /**
   * @param gridx The x position of the component.
   * @param gridy The y position of the component.
   */
  def this(gridx: Int, gridy: Int) =
    this(gridx)
    this.gridy = gridy

  /**
   * @param gridx The x position of the component.
   * @param gridy The y position of the component.
   * @param topInsets The top insets of the component.
   * @param bottomInsets The bottom insets of the component.
   */
  def this(gridx: Int, gridy: Int, topInsets: Int, bottomInsets: Int) =
    this(gridx, gridy)
    this.insets = new Insets(topInsets, 0, bottomInsets, 0)

  /**
   * @param gridx The x position of the component.
   * @param gridy The y position of the component.
   * @param gridwidth The width of the component.
   */
  def this(gridx: Int, gridy: Int, gridwidth: Int) =
    this(gridx, gridy)
    this.gridwidth = gridwidth

  /**
   * @param gridx The x position of the component.
   * @param gridy The y position of the component.
   * @param gridwidth The width of the component.
   * @param gridheight The height of the component.
   * @param insets The insets of the component.
   */
  def this(gridx: Int, gridy: Int, gridwidth: Int, gridheight: Int, insets: Insets) =
    this(gridx, gridy)
    this.gridwidth = gridwidth
    this.gridheight = gridheight

  /**
   * @param gridx The x position of the component.
   * @param gridy The y position of the component.
   * @param gridwidth The width of the component.
   * @param insets The insets of the component.
   */
  def this(gridx: Int, gridy: Int, gridwidth: Int, insets: Insets) =
    this(gridx, gridy, gridwidth)
    this.insets = insets
