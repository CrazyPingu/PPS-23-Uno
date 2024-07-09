package view.game

/**
 * A scala object that contains the coordinates of the game panel
 */
object CoordinateHandler:
  val panelGridDimension: (Int, Int) = (27, 9)

  val deckCoordinate: (Int, Int) = (panelGridDimension(0) / 2 - 1, panelGridDimension(1) / 2)

  val usedCardCoordinate: (Int, Int) = (panelGridDimension(0) / 2 + 1, deckCoordinate(1))

