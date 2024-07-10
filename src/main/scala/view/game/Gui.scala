package view.game

import controller.Controller
import model.Hand
import utils.ImageHandler.{backgroundTable, retroCards}
import utils.PlayerTypes.Bot2
import utils.{ImageHandler, PlayerTypes}
import view.game.Cell.{CardCell, Cell, DeckCell, DirectionCell, UnoCell, UsedCardCell}
import view.game.CoordinateHandler.*

import java.awt.{Graphics, Graphics2D, GridLayout}
import javax.swing.JPanel
import scala.collection.{immutable, mutable}

/**
 * The graphical user interface of the game
 *
 * @param controller the controller of the game
 * @param hands      the hands of the player and the bots (1 player and 3 bots)
 */
class Gui(controller: Controller, hands: immutable.Map[PlayerTypes, Hand]) extends JPanel:
  private val layout: GridLayout = new GridLayout(panelGridDimension(1), panelGridDimension(0))
  private val unoButton = new UnoCell(controller)
  private val directionCell = new DirectionCell
  private val cells: mutable.Map[(Int, Int), Cell] = mutable.Map()
  setLayout(layout)
  createGui()

  /**
   * Create the GUI adding all the components
   */
  private def createGui(): Unit =
    this.removeAll()
    val lastRow = layout.getRows - 1
    val lastCol = layout.getColumns - 1

    for row <- 0 until layout.getRows; col <- 0 until layout.getColumns do
      val cell = (row, col) match
        case (r, c) if r == deckCoordinate(1) && c == deckCoordinate(0)         => new DeckCell(controller)
        case (r, c) if r == usedCardCoordinate(1) && c == usedCardCoordinate(0) => new UsedCardCell(controller)
        case (r, c) if r == unoCallCoordinate(1) && c == unoCallCoordinate(0)   => unoButton
        case (r, c) if r == directionCellCoordinate(1) && c == directionCellCoordinate(0) => directionCell
        case (`lastRow`, c)                                                               => new CardCell()
        case _                                                                            => new Cell()

      cells += (row, col) -> cell
      this.add(cell)

    updateGui()
    this.validate()
    this.repaint()

  /**
   * Update the GUI in case of changes
   */
  def updateGui(): Unit =
    val lastRow = layout.getRows - 1
    val lastCol = layout.getColumns - 1

    val startingPositions = Map(
      PlayerTypes.Bot1 -> (layout.getRows - hands(PlayerTypes.Bot1).size) / 2,
      PlayerTypes.Bot2 -> (layout.getColumns - hands(PlayerTypes.Bot2).size) / 2,
      PlayerTypes.Bot3 -> (layout.getRows - hands(PlayerTypes.Bot3).size) / 2,
      PlayerTypes.Player -> (layout.getColumns - hands(PlayerTypes.Player).size) / 2
    )

    for row <- 0 until layout.getRows; col <- 0 until layout.getColumns do
      (row, col) match
//      Bot2 hand
        case (0, c) if isWithinHand(c, startingPositions(PlayerTypes.Bot2), hands(PlayerTypes.Bot2)) =>
          cells(0, c).setIcon(retroCards)
        case (0, c) => cells(0, c).setIcon(null)

//      Player hand
        case (`lastRow`, c) if isWithinHand(c, startingPositions(PlayerTypes.Player), hands(PlayerTypes.Player)) =>
          hands(PlayerTypes.Player)
            .lift(c - startingPositions(PlayerTypes.Player))
            .foreach:
              card =>
                cells(lastRow, c) match
                  case cardCell: CardCell => cardCell.applyCard(card)
                  case _                  => ()
        case (`lastRow`, c) =>
          hands(PlayerTypes.Player)
            .lift(c - startingPositions(PlayerTypes.Player))
            .foreach:
              card =>
                cells(lastRow, c) match
                  case cardCell: CardCell => cardCell.removeCard()
                  case _                  => ()

//      Bot1 hand
        case (r, 0) if isWithinHand(r, startingPositions(PlayerTypes.Bot1), hands(PlayerTypes.Bot1)) =>
          cells(r, 0).setIcon(retroCards)
        case (r, 0) =>
          cells(r, 0).setIcon(null)

//      Bot3 hand
        case (r, `lastCol`) if isWithinHand(r, startingPositions(PlayerTypes.Bot3), hands(PlayerTypes.Bot3)) =>
          cells(r, `lastCol`).setIcon(retroCards) // Bot3 hand placeholder
        case (r, `lastCol`) =>
          cells(r, `lastCol`).setIcon(null)

        case _ => ()

  /**
   * Toggle the visibility of the uno button
   *
   * @param show true to show the button, false to hide it
   */
  def toggleVisibilityUnoButton(show: Boolean): Unit = unoButton.setVisible(show)

  /**
   * Reverse the direction of the game
   */
  def reverseDirection(): Unit = directionCell.reverseDirection()

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

  override def paintComponent(g: Graphics): Unit =
    super.paintComponent(g)
    val g2d: Graphics2D = g.asInstanceOf[Graphics2D]
    g2d.drawImage(backgroundTable, 0, 0, getWidth, getHeight, this)
