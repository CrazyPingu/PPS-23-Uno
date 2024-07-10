package view.game.Cell

import utils.ImageHandler
import utils.Rotation.{FLIP_HORIZONTAL, NONE}

/**
 * A cell that represents the direction of the game
 */
class DirectionCell extends Cell:
  private var dir = true
  setIcon(ImageHandler.direction)

  /**
   * Reverse the direction of the cell
   */
  def reverseDirection(): Unit =
    setIcon(ImageHandler.direction, if dir then FLIP_HORIZONTAL else NONE)
    dir = !dir
