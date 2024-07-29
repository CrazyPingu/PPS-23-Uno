package view

import java.awt.Insets

class GridBagConstraints extends java.awt.GridBagConstraints:

  def this(gridx: Int, gridy: Int) =
    this()
    this.gridx = gridx
    this.gridy = gridy

  def this(gridx: Int, gridy: Int, topInsets: Int, bottomInsets: Int) =
    this(gridx, gridy)
    this.insets = new Insets(topInsets, 0, bottomInsets, 0)

  def this(gridx: Int, gridy: Int, gridwidth: Int) =
    this(gridx, gridy)
    this.gridwidth = gridwidth

  def this(gridx: Int, gridy: Int, gridwidth: Int, gridheight: Int, insets: Insets) =
    this(gridx, gridy)
    this.gridwidth = gridwidth  
    this.gridheight = gridheight

  def this(gridx: Int, gridy: Int, gridwidth: Int, insets: Insets) =
    this(gridx, gridy, gridwidth)
    this.insets = insets
