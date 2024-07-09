package view.game.Cell

import controller.Controller
import utils.ImageHandler

class DeckCell(controller: Controller) extends Cell:
  setIcon(ImageHandler.retroCards)

  addActionListener(_ =>
    println("DeckCell clicked!")
    controller.drawCard()
  )


