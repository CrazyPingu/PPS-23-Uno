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
  def setIcon(img: Image, rotation: Rotation): Unit =
    if img == null then super.setIcon(null)
    else if img != null && getWidth > 0 && getHeight > 0 then
      super.setIcon(
        new ImageIcon(rotateImage(img, rotation).getScaledInstance(getWidth, getHeight, Image.SCALE_SMOOTH))
      )
    else if img != null then
      addComponentListener(
        new ComponentAdapter:
          override def componentResized(e: java.awt.event.ComponentEvent): Unit =
            setIcon(new ImageIcon(img.getScaledInstance(getWidth, getHeight, Image.SCALE_SMOOTH)))
            removeComponentListener(this)
      )

  def setIcon(img: Image): Unit =
    setIcon(img, NONE)
