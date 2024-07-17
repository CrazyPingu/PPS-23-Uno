package view.game

import controller.Controller
import model.Hand
import model.bot.{BotPlayer, EasyBotPlayerImpl}
import model.cards.Card
import utils.ImageHandler.{backgroundTable, retroCards, rotateImage}
import utils.Rotation.{FLIP_VERTICAL, NONE, ROTATE_LEFT, ROTATE_RIGHT}
import utils.{ImageHandler, Rotation}
import view.game.Cell.{CardCell, Cell, DeckCell, DirectionCell, UnoCell, UsedCardCell}
import view.game.CoordinateHandler.*

import java.awt.{Component, Graphics, Graphics2D, GridLayout}
import javax.swing.JPanel

/**
 * The graphical user interface of the game
 *
 * @param controller the controller of the game
 */
class Gui(controller: Controller) extends JPanel:
  private val layout: GridLayout = new GridLayout(panelGridDimension(1), panelGridDimension(0))
  private val unoButton = new UnoCell(controller)
  private val directionCell = new DirectionCell
  private val usedCardCell = new UsedCardCell
  setLayout(layout)

  private var bot1 = Option.empty[BotPlayer]
  private var bot2 = Option.empty[BotPlayer]
  private var bot3 = Option.empty[BotPlayer]
  private var player = Option.empty[Hand]

  def setEntity(bot1: BotPlayer, bot2: BotPlayer, bot3: BotPlayer, player: Hand): Unit =
    this.bot1 = Option(bot1)
    this.bot2 = Option(bot2)
    this.bot3 = Option(bot3)
    this.player = Option(player)
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
        case (r, c) if r == deckCoordinate(1) && c == deckCoordinate(0)                   => new DeckCell(controller)
        case (r, c) if r == usedCardCoordinate(1) && c == usedCardCoordinate(0)           => usedCardCell
        case (r, c) if r == unoCallCoordinate(1) && c == unoCallCoordinate(0)             => unoButton
        case (r, c) if r == directionCellCoordinate(1) && c == directionCellCoordinate(0) => directionCell
        case (`lastRow`, c)                                                               => new CardCell(controller)
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

    for i <- 0 until getComponentCount do
      val component = getComponent(i)

      (i / layout.getColumns, i % layout.getColumns) match
//        Bot2 hand top row
        case (0, c) =>
          applyIcon(
            component,
            c - (layout.getColumns - bot2.get.size) / 2,
            isWithinHand(c, (layout.getColumns - bot2.get.size) / 2, bot2.get.size),
            FLIP_VERTICAL
          )

//        Player hand bottom row
        case (r, c) if r == lastRow => applyIcon(component, c - (layout.getColumns - player.get.size) / 2)

//        Bot1 hand left column
        case (r, 0) =>
          applyIcon(
            component,
            r - (layout.getRows - bot1.get.size) / 2,
            isWithinHand(r, (layout.getRows - bot1.get.size) / 2, bot1.get.size),
            ROTATE_RIGHT
          )

//        Bot3 hand right column
        case (r, `lastCol`) =>
          applyIcon(
            component,
            r - (layout.getRows - bot3.get.size) / 2,
            isWithinHand(r, (layout.getRows - bot3.get.size) / 2, bot3.get.size),
            ROTATE_LEFT
          )

        case _ => ()

  /**
   * Toggle the visibility of the uno button
   *
   * @param show true to show the button, false to hide it
   */
  def toggleVisibilityUnoButton(show: Boolean): Unit = unoButton.setVisible(show)

  /**
   * Block the player from performing every action
   *
   * @param toggle false to block the player, true to allow the player to perform actions
   */
  def allowPlayerAction(toggle: Boolean): Unit =
//    Block the cell containing the deck
    getComponent(deckCoordinate(1) * layout.getColumns + deckCoordinate(0)).setEnabled(toggle)
//    Toggle last row
    for i <- 0 until layout.getColumns do getComponent((layout.getRows - 1) * layout.getColumns + i).setEnabled(toggle)

  /**
   * Reverse the direction of the game
   */
  def reverseDirection(): Unit = directionCell.reverseDirection()

  /**
   * Dispose a card in the used card cell
   *
   * @param card the card to dispose
   */
  def disposeCard(card: Card): Unit = usedCardCell.disposeCard(card)

  /**
   * Check if a position is within a hand
   *
   * @param position      the position to check
   * @param startPosition the start position of the hand
   * @return true if the position is within the hand, false otherwise
   */
  private def isWithinHand(position: Int, startPosition: Int, hSize: Int): Boolean =
    position >= startPosition && position < startPosition + hSize

  /**
   * Apply the icon to the component understanding if it is a card or a cell
   *
   * @param component the component to apply the icon
   * @param indexInHand the index of the possible card in the hand of the player
   * @param action true if you want to show the card / set the retro of the card visible, false otherwise
   * @param rotation the rotation of the card
   */
  private def applyIcon(component: Component, indexInHand: Int, action: Boolean, rotation: Rotation): Unit =
    component match
      case cell: CardCell =>
        player.get
          .lift(indexInHand) match
          case Some(card) => cell.applyCard(card)
          case None       => cell.removeCard()
      case cell: Cell if action => cell.setIcon(rotateImage(retroCards, rotation))
      case cell: Cell           => cell.setIcon(null)
      case _                    => ()

  /**
   * Apply the icon to the component understanding if it is a card or a cell
   *
   * @param component the component to apply the icon
   * @param indexInHand the index of the possible card in the hand of the player
   */
  private def applyIcon(component: Component, indexInHand: Int): Unit =
    applyIcon(component, indexInHand, true, NONE)

  override def paintComponent(g: Graphics): Unit =
    super.paintComponent(g)
    val g2d: Graphics2D = g.asInstanceOf[Graphics2D]
    g2d.drawImage(backgroundTable, 0, 0, getWidth, getHeight, this)
