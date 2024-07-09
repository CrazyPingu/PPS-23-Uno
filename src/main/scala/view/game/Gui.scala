package view.game

import controller.Controller
import model.Hand
import utils.ImageHandler.backgroundTable
import utils.PlayerTypes.Bot2
import utils.{ImageHandler, PlayerTypes}
import view.game.Cell.{CardCell, Cell, DeckCell, DirectionCell, UnoCell, UsedCardCell}
import view.game.CoordinateHandler.*

import java.awt.{Graphics, Graphics2D, GridLayout, Image}
import javax.swing.JPanel
import scala.collection.immutable

/**
 * The graphical user interface of the game
 *
 * @param controller the controller of the game
 * @param hands      the hands of the player and the bots (1 player and 3 bots)
 */
class Gui(controller: Controller, hands: immutable.Map[PlayerTypes, Hand]) extends JPanel:
  private val _backgroundImage: Image = backgroundTable
  private val _layout: GridLayout = new GridLayout(panelGridDimension(1), panelGridDimension(0))
  private val _unoButton = new UnoCell(controller)
  private val _directionCell = new DirectionCell
  setLayout(_layout)
  updateGui()

  /**
   * Refresh the GUI, removing all the components and adding them again
   */
  def updateGui(): Unit =
    this.removeAll()

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
        case (r, c) if r == deckCoordinate(1) && c == deckCoordinate(0)         => new DeckCell(controller)
        case (r, c) if r == usedCardCoordinate(1) && c == usedCardCoordinate(0) => new UsedCardCell(controller)
        case (r, c) if r == unoCallCoordinate(1) && c == unoCallCoordinate(0)   => _unoButton
        case (r, c) if r == directionCellCoordinate(1) && c == directionCellCoordinate(0) => _directionCell
        case (0, c) if isWithinHand(c, startingPositions(PlayerTypes.Bot2), hands(PlayerTypes.Bot2)) =>
          new Cell(ImageHandler.retroCards) // Bot2 hand placeholder
        case (`lastRow`, c) if isWithinHand(c, startingPositions(PlayerTypes.Player), hands(PlayerTypes.Player)) =>
          new CardCell(hands(PlayerTypes.Player)(c - startingPositions(PlayerTypes.Player)))
        case (r, 0) if isWithinHand(r, startingPositions(PlayerTypes.Bot1), hands(PlayerTypes.Bot1)) =>
          new Cell(ImageHandler.retroCards) // Bot1 hand placeholder
        case (r, `lastCol`) if isWithinHand(r, startingPositions(PlayerTypes.Bot3), hands(PlayerTypes.Bot3)) =>
          new Cell(ImageHandler.retroCards) // Bot3 hand placeholder
        case _ => new Cell

      this.add(cell)

    this.validate()
    this.repaint()

  /**
   * Toggle the visibility of the uno button
   *
   * @param show true to show the button, false to hide it
   */
  def toggleVisibilityUnoButton(show: Boolean): Unit = _unoButton.setVisible(show)

  /**
   * Reverse the direction of the game
   */
  def reverseDirection(): Unit = _directionCell.reverseDirection()

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
    g2d.drawImage(_backgroundImage, 0, 0, getWidth, getHeight, this)
