package view.game.Cell

import controller.GameLoop
import utils.ImageHandler

/**
 * The cell that is used to call UNO when the player has only one card left
 */
class UnoCell(gameLoop: GameLoop) extends Cell:
  setIcon(ImageHandler.unoButton)

  addActionListener(
    _ => gameLoop.callUno()
  )

  /**
   * Set the icon of the cell to the checked or unchecked icon
   *
   * @param checked true if the icon should be the checked icon, false otherwise
   */
  def setChecked(checked: Boolean): Unit =
    setIcon(if checked then ImageHandler.check else ImageHandler.unoButton)
