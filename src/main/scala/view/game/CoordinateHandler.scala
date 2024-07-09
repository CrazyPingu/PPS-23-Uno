package view.game

/**
 * A scala object that contains the coordinates of the game panel
 */
object CoordinateHandler:
  /**
   * The dimension of the panel grid
   */
  val panelGridDimension: (Int, Int) = (27, 11)

  /**
   * The coordinate of the deck
   */
  val deckCoordinate: (Int, Int) = (panelGridDimension(0) / 2 - 1, panelGridDimension(1) / 2)

  /**
   * The coordinate of the used card
   */
  val usedCardCoordinate: (Int, Int) = (panelGridDimension(0) / 2 + 1, deckCoordinate(1))

  /**
   * The coordinate of the button to call uno
   */
  val unoCallCoordinate: (Int, Int) = (panelGridDimension(0) - 5, panelGridDimension(1) - 3)
