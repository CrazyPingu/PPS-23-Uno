package view.game.Cell

import utils.{ImageHandler, Rotation}

/**
 * A cell that represents the direction of the game
 *
 * @param rotation the rotation of the cell
 */
class TurnIndicatorCell(private val rotation: Rotation) extends Cell:

  /**
   * When enabled, the cell displays the turn arrow
   */
  override def setEnabled(b: Boolean): Unit =
    super.setEnabled(b)
    setIcon(if b then ImageHandler.turnArrow else null, rotation)
