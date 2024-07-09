package view.game.Cell

import controller.Controller
import utils.{Color, ImageHandler}

class UsedCardCell(controller: Controller) extends Cell:

  setIcon(ImageHandler.loadCardImage("Wild", Color.Black))


  addActionListener(_ => println("Used card cell clicked!"))


