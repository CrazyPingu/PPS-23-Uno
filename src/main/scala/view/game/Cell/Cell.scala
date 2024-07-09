package view.game.Cell

import java.awt.Image
import java.awt.event.ComponentAdapter
import javax.swing.{ImageIcon, JButton}

/**
 * A cell of the game
 */
class Cell extends JButton:
  private var _storedImg: Option[Image] = None

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
  def setIcon(img: Image): Unit =
    _storedImg = Some(img)
    if getWidth > 0 && getHeight > 0 then
      super.setIcon(new ImageIcon(img.getScaledInstance(getWidth, getHeight, Image.SCALE_SMOOTH)))
    else
      addComponentListener(
        new ComponentAdapter:
          override def componentResized(e: java.awt.event.ComponentEvent): Unit =
            setIcon(new ImageIcon(img.getScaledInstance(getWidth, getHeight, Image.SCALE_SMOOTH)))
            removeComponentListener(this)
      )
