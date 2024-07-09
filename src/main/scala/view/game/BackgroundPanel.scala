package view.game

import utils.ImageHandler.backgroundTable

import java.awt.{Graphics, Graphics2D, Image, LayoutManager}
import javax.swing.JPanel

class BackgroundPanel(layout: LayoutManager) extends JPanel:
  super.setLayout(layout)
  private val backgroundImage: Image = backgroundTable


  override def paintComponent(g: Graphics): Unit =
    super.paintComponent(g)
    val g2d: Graphics2D = g.asInstanceOf[Graphics2D]
    g2d.drawImage(backgroundImage, 0, 0, getWidth, getHeight, this)

