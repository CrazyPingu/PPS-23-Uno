package view.game.Cell

import utils.ImageHandler.rotateImage
import utils.Rotation
import utils.Rotation.NONE

import java.awt.Image
import java.awt.event.{ComponentAdapter, ComponentEvent}
import javax.swing.{ImageIcon, JButton}

/**
 * A cell of the game
 */
class Cell extends JButton:
  setBorderPainted(false)
  setContentAreaFilled(false)
  setFocusPainted(false)
  setOpaque(false)

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
    else if getWidth > 0 && getHeight > 0 then updateIcon(img, rotation)
    else
      addComponentListener(
        new ComponentAdapter:
          override def componentResized(e: ComponentEvent): Unit =
            updateIcon(img, rotation)
            removeComponentListener(this)
      )

  /**
   * Update the icon of the cell based on current dimensions and rotation.
   */
  private def updateIcon(img: Image, rotation: Rotation): Unit =
    val rotatedImage: Image = rotateImage(img, rotation)
    val imageIcon: ImageIcon = new ImageIcon(rotatedImage.getScaledInstance(getWidth, getHeight, Image.SCALE_SMOOTH))
    super.setIcon(imageIcon)
    super.setDisabledIcon(imageIcon)
