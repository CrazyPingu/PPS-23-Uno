package view.game.Cell

import controller.GameLoop
import utils.ImageHandler

/**
 * The cell that represents the deck of the game
 */
class DeckCell(private val gameLoop: GameLoop) extends Cell:
  setIcon(ImageHandler.retroCards)

  addActionListener(
    _ => gameLoop.drawCard()
  )
