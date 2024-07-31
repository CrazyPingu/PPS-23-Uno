package view.game.Cell

import controller.GameController
import utils.ImageHandler

/**
 * The cell that is used to call UNO when the player has only one card left
 */
class UnoCell extends Cell:
  setIcon(ImageHandler.unoButton)

  addActionListener(
    _ => GameController.callUno()
  )

  def setChecked(checked: Boolean): Unit =
    setIcon(if checked then ImageHandler.check else ImageHandler.unoButton)
