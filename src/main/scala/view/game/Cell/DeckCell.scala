package view.game.Cell

import controller.Controller
import utils.ImageHandler

/**
 * The cell that represents the deck of the game
 * 
 * @param controller the controller of the game
 */
class DeckCell(controller: Controller) extends Cell:
  setIcon(ImageHandler.retroCards)

  addActionListener(_ =>
    println("DeckCell clicked!")
    controller.drawCard()
  )


