package view.game.Cell

import utils.{ImageHandler, Rotation}

class TurnIndicatorCell(private val rotation: Rotation) extends Cell:

  override def setEnabled(b: Boolean): Unit =
    super.setEnabled(b)
    setIcon(if b then ImageHandler.turnArrow else null, rotation)
