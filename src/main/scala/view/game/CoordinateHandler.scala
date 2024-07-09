package view.game

object CoordinateHandler:
  val panelGridDimension: (Int, Int) = (27, 9)

  val deckCoordinate: (Int, Int) = (panelGridDimension(0) / 2 - 1, panelGridDimension(1) / 2)

  val usedCardCoordinate: (Int, Int) = (panelGridDimension(0) / 2 + 1, deckCoordinate(1))

