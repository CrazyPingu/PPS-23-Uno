package view.game.Cell

import controller.GameController
import utils.ImageHandler

/**
 * The cell that represents the deck of the game
 */
class DeckCell extends Cell:
  setIcon(ImageHandler.retroCards)

  addActionListener(
    _ => GameController.drawCard()
  )
