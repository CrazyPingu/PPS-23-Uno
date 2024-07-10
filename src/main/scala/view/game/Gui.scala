package view.game

import controller.Controller
import model.Hand
import utils.ImageHandler.{backgroundTable, retroCards}
import utils.PlayerTypes.Bot2
import utils.{ImageHandler, PlayerTypes}
import view.game.Cell.{CardCell, Cell, DeckCell, DirectionCell, UnoCell, UsedCardCell}
import view.game.CoordinateHandler.*

import java.awt.{Component, Graphics, Graphics2D, GridLayout}
import javax.swing.JPanel
import scala.collection.immutable

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

    for i <- 0 until getComponentCount do
      val component = getComponent(i)

      (i / layout.getColumns, i % layout.getColumns) match
//        Bot2 hand
        case (0, c) if isWithinHand(c, startingPositions(PlayerTypes.Bot2), hands(PlayerTypes.Bot2)) =>
          applyIcon(component, c - startingPositions(PlayerTypes.Bot2), Bot2, true)
        case (0, c) => applyIcon(component, c - startingPositions(PlayerTypes.Bot2), Bot2, false)

//        Player hand
        case (`lastRow`, c) if isWithinHand(c, startingPositions(PlayerTypes.Player), hands(PlayerTypes.Player)) =>
          applyIcon(component, c - startingPositions(PlayerTypes.Player), PlayerTypes.Player, true)
        case (`lastRow`, c) =>
          applyIcon(component, c - startingPositions(PlayerTypes.Player), PlayerTypes.Player, false)

//        Bot1 hand
        case (r, 0) if isWithinHand(r, startingPositions(PlayerTypes.Bot1), hands(PlayerTypes.Bot1)) =>
          applyIcon(component, r - startingPositions(PlayerTypes.Bot1), PlayerTypes.Bot1, true)
        case (r, 0) => applyIcon(component, r - startingPositions(PlayerTypes.Bot1), PlayerTypes.Bot1, false)

//        Bot3 hand
        case (r, `lastCol`) if isWithinHand(r, startingPositions(PlayerTypes.Bot3), hands(PlayerTypes.Bot3)) =>
          applyIcon(component, r - startingPositions(PlayerTypes.Bot3), PlayerTypes.Bot3, true)
        case (r, `lastCol`) => applyIcon(component, r - startingPositions(PlayerTypes.Bot3), PlayerTypes.Bot3, false)

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

  /**
   * Apply the icon to the component understanding if it is a card or a cell
   *
   * @param component the component to apply the icon
   * @param indexInHand the index of the possible card in the hand of the player
   * @param playerTypes the type of the player
   * @param action true if you want to show the card / set the retro of the card visible, false otherwise
   */
  private def applyIcon(component: Component, indexInHand: Int, playerTypes: PlayerTypes, action: Boolean): Unit =
    hands(playerTypes)
      .lift(indexInHand)
      .foreach:
        card =>
          component match
            case cardCell: CardCell => if action then cardCell.applyCard(card) else cardCell.removeCard()
            case _                  => component.asInstanceOf[Cell].setIcon(if action then retroCards else null)

  override def paintComponent(g: Graphics): Unit =
    super.paintComponent(g)
    val g2d: Graphics2D = g.asInstanceOf[Graphics2D]
    g2d.drawImage(backgroundTable, 0, 0, getWidth, getHeight, this)
