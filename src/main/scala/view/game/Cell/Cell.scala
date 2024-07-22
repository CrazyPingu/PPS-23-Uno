package view.game.Cell

import utils.ImageHandler.rotateImage
import utils.Rotation
import utils.Rotation.NONE

import java.awt.Image
import java.awt.event.ComponentAdapter
import javax.swing.{ImageIcon, JButton}

/**
 * A cell of the game
 */
class Cell extends JButton:
  setBorderPainted(false)
  setContentAreaFilled(false)
  setFocusPainted(false)
  setOpaque(false)
  setContentAreaFilled(false)

  /**
   * Constructor of the cell
   *
   * @param img the image of the cell
   */
  def this(img: Image) =
    this()
    setIcon(img)

  /**
   * Set the icon of the cell
   *
   * @param img the image of the cell
   */
  def setIcon(img: Image, rotation: Rotation = NONE): Unit =
    if img == null then
      super.setIcon(null)
      super.setDisabledIcon(null)
    else if getWidth > 0 && getHeight > 0 then
      val rotatedImage: Image = rotateImage(img, rotation)
      val image: ImageIcon = new ImageIcon(rotatedImage.getScaledInstance(getWidth, getHeight, Image.SCALE_SMOOTH))
      super.setIcon(image)
      super.setDisabledIcon(image)
    else
      addComponentListener(
        new ComponentAdapter:
          override def componentResized(e: java.awt.event.ComponentEvent): Unit =
            val image: ImageIcon =
              new ImageIcon(rotateImage(img, rotation).getScaledInstance(getWidth, getHeight, Image.SCALE_SMOOTH))
            setIcon(image)
            setDisabledIcon(image)
            removeComponentListener(this)
      )
