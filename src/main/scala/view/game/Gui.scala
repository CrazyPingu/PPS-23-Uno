package view.game

import controller.Controller
import model.Hand
import utils.PlayerTypes.Bot2
import utils.{ImageHandler, PlayerTypes}
import view.Frame
import view.game.Cell.{CardCell, Cell, DeckCell, UsedCardCell}
import view.game.CoordinateHandler.{deckCoordinate, panelGridDimension, usedCardCoordinate}

import java.awt.GridLayout
import javax.swing.JPanel
import scala.collection.immutable

/**
 * The graphical user interface of the game
 *
 * @param controller the controller of the game
 * @param hands      the hands of the player and the bots (1 player and 3 bots)
 */
class Gui(controller: Controller, hands: immutable.Map[PlayerTypes, Hand]):
  private val _frame: Frame = new Frame
  private val _layout: GridLayout = new GridLayout(panelGridDimension(1), panelGridDimension(0))
  private val _panel: JPanel = new BackgroundPanel(_layout)

  updateGui()

  _frame.add(_panel)
  _frame.setLocationRelativeTo(null)
  _frame.setVisible(true)

  /**
   * Update the GUI
   *
   * @param hands      the hands of the player and the bots (1 player and 3 bots)
   * @param controller the controller of the game
   */
  def updateGui(): Unit =
    _panel.removeAll()

    val lastRow = _layout.getRows - 1
    val lastCol = _layout.getColumns - 1

    val startingPositions = Map(
      PlayerTypes.Bot1 -> (_layout.getRows - hands(PlayerTypes.Bot1).size) / 2,
      PlayerTypes.Bot2 -> (_layout.getColumns - hands(PlayerTypes.Bot2).size) / 2,
      PlayerTypes.Bot3 -> (_layout.getRows - hands(PlayerTypes.Bot3).size) / 2,
      PlayerTypes.Player -> (_layout.getColumns - hands(PlayerTypes.Player).size) / 2
    )

    for row <- 0 until _layout.getRows; col <- 0 until _layout.getColumns do
      val cell = (row, col) match
        case (r, c) if r == deckCoordinate(1) && c == deckCoordinate(0) => new DeckCell(controller)
        case (r, c) if r == usedCardCoordinate(1) && c == usedCardCoordinate(0) => new UsedCardCell(controller)
        case (0, c) if isWithinHand(c, startingPositions(PlayerTypes.Bot2), hands(PlayerTypes.Bot2)) =>
          new Cell(ImageHandler.retroCards) // Bot2 hand placeholder
        case (`lastRow`, c) if isWithinHand(c, startingPositions(PlayerTypes.Player), hands(PlayerTypes.Player)) =>
          new CardCell(hands(PlayerTypes.Player)(c - startingPositions(PlayerTypes.Player)))
        case (r, 0) if isWithinHand(r, startingPositions(PlayerTypes.Bot1), hands(PlayerTypes.Bot1)) =>
          new Cell(ImageHandler.retroCards) // Bot1 hand placeholder
        case (r, `lastCol`) if isWithinHand(r, startingPositions(PlayerTypes.Bot3), hands(PlayerTypes.Bot3)) =>
          new Cell(ImageHandler.retroCards) // Bot3 hand placeholder
        case _ => new Cell

      _panel.add(cell)

    _panel.validate()
    _panel.repaint()


  /**
   * Check if a position is within a hand
   *
   * @param position      the position to check
   * @param startPosition the start position of the hand
   * @param hand          the hand to check
   * @return true if the position is within the hand, false otherwise
   */
  private def isWithinHand(position: Int, startPosition: Int, hand: Hand): Boolean =
    position >= startPosition && position < startPosition + hand.size
