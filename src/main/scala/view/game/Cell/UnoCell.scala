package view.game.Cell

import controller.Controller
import utils.ImageHandler

/**
 * The cell that is used to call UNO when the player has only one card left
 *
 * @param controller the controller of the games
 */
class UnoCell(controller: Controller) extends Cell:
  setIcon(ImageHandler.unoButton)

  addActionListener(
    _ => println("UnoCell clicked!")
  )
