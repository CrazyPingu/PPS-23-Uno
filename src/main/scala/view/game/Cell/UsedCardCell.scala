package view.game.Cell

import controller.Controller
import utils.{Color, ImageHandler}

/**
 * The cell that represents the used card of the game
 * 
 * @param controller the controller of the game
 */
class UsedCardCell(controller: Controller) extends Cell:

  setIcon(ImageHandler.loadCardImage("Wild", Color.Black))


  addActionListener(_ => println("Used card cell clicked!"))


