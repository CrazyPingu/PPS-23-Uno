package view.game

/**
 * A scala object that contains the coordinates of the game panel
 */
object CoordinateHandler:
  /**
   * The dimension of the panel grid
   */
  val panelGridDimension: (Int, Int) = (27, 11)

  val arrowCoordinate: List[(Int, Int)] = List(
    (panelGridDimension(1) - 3, panelGridDimension(0) / 2),
    (panelGridDimension(1) / 2, 2),
    (2, panelGridDimension(0) / 2),
    (panelGridDimension(1) / 2, panelGridDimension(0) - 3)
  )

  /**
   * The coordinate of the deck
   */
  val deckCoordinate: (Int, Int) = (panelGridDimension(0) / 2 - 2, panelGridDimension(1) / 2)

  /**
   * The coordinate of the used card
   */
  val usedCardCoordinate: (Int, Int) = (panelGridDimension(0) / 2 - 1, panelGridDimension(1) / 2)

  /**
   * The coordinate of the button to call uno
   */
  val unoCallCoordinate: (Int, Int) = (panelGridDimension(0) / 2 + 1, panelGridDimension(1) / 2)

  /**
   * The coordinate that contains the image of the direction of the game
   */
  val directionCellCoordinate: (Int, Int) = (panelGridDimension(0) - 5, 2)
