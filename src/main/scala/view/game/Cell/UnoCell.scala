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
  setIcon(ImageHandler.unoButton)
  setVisible(_visible)

  addActionListener(
    _ => if _visible then println("UnoCell clicked!")
  )

  override def setVisible(value: Boolean): Unit =
    super.setVisible(value)
    _visible = value
