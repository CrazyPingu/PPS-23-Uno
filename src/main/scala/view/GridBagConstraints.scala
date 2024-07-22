package view

class GridBagConstraints extends java.awt.GridBagConstraints:

  def this(gridx: Int, gridy: Int) =
    this()
    this.weightx = 1
    this.weighty = 1
    this.gridx = gridx
    this.gridy = gridy

  def this(gridx: Int, gridy: Int, topInsets: Int, bottomInsets: Int) =
    this(gridx, gridy)
    this.insets = new java.awt.Insets(topInsets, 0, bottomInsets, 0)
