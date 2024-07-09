package view.game.Cell

import controller.Controller
import utils.ImageHandler

/**
 * The cell that is used to call UNO when the player has only one card left
 *
 * @param controller the controller of the games
 */
class UnoCell(controller: Controller) extends Cell:
  private var _visible: Boolean = false

  addActionListener(
    _ => if _visible then println("UnoCell clicked!")
  )

  /**
   * Set the visibility of the cell
   *
   * @param value the value to set the visibility to
   */
  override def setVisible(value: Boolean): Unit =
    _visible = value
    if value then setIcon(ImageHandler.retroCards) else setIcon(null)
