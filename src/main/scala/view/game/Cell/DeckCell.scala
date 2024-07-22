package view.game.Cell

import controller.GameController
import utils.ImageHandler

/**
 * The cell that represents the deck of the game
 *
 * @param controller the controller of the game
 */
class DeckCell(controller: GameController) extends Cell:
  setIcon(ImageHandler.retroCards)

  addActionListener(
    _ => controller.drawCard()
  )
