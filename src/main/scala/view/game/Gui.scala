package view.game

import controller.Controller
import model.Hand
import model.bot.{BotPlayer, EasyBotPlayerImpl}
import model.cards.Card
import utils.ImageHandler.{backgroundTable, retroCards, rotateImage}
import utils.PlayerTypes.Bot2
import utils.Rotation.{FLIP_VERTICAL, NONE, ROTATE_LEFT, ROTATE_RIGHT}
import utils.{ImageHandler, PlayerTypes, Rotation}
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
    println("Entity set")
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

    val startingPositions = Map(
      PlayerTypes.Bot1 -> (layout.getRows - bot1.get.size) / 2,
      PlayerTypes.Bot2 -> (layout.getColumns - bot2.get.size) / 2,
      PlayerTypes.Bot3 -> (layout.getRows - bot3.get.size) / 2,
      PlayerTypes.Player -> (layout.getColumns - player.get.size) / 2
    )

    for i <- 0 until getComponentCount do
      val component = getComponent(i)

      (i / layout.getColumns, i % layout.getColumns) match
//        Bot2 hand top row
        case (0, c) if isWithinHand(c, startingPositions(PlayerTypes.Bot2), bot2.get) =>
          applyIcon(component, c - startingPositions(PlayerTypes.Bot2), Bot2, true, FLIP_VERTICAL)
        case (0, c) => applyIcon(component, c - startingPositions(PlayerTypes.Bot2), Bot2, false, FLIP_VERTICAL)

//        Player hand bottom row
        case (`lastRow`, c) if isWithinHand(c, startingPositions(PlayerTypes.Player), player.get) =>
          applyIcon(component, c - startingPositions(PlayerTypes.Player), PlayerTypes.Player, true)
        case (`lastRow`, c) =>
          applyIcon(component, c - startingPositions(PlayerTypes.Player), PlayerTypes.Player, false)

//        Bot1 hand left column
        case (r, 0) if isWithinHand(r, startingPositions(PlayerTypes.Bot1), bot1.get) =>
          applyIcon(component, r - startingPositions(PlayerTypes.Bot1), PlayerTypes.Bot1, true, ROTATE_RIGHT)
        case (r, 0) =>
          applyIcon(component, r - startingPositions(PlayerTypes.Bot1), PlayerTypes.Bot1, false, ROTATE_RIGHT)

//        Bot3 hand right column
        case (r, `lastCol`) if isWithinHand(r, startingPositions(PlayerTypes.Bot3), bot3.get) =>
          applyIcon(component, r - startingPositions(PlayerTypes.Bot3), PlayerTypes.Bot3, true, ROTATE_LEFT)
        case (r, `lastCol`) =>
          applyIcon(component, r - startingPositions(PlayerTypes.Bot3), PlayerTypes.Bot3, false, ROTATE_LEFT)

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
   * @param playerType the type of the player
   * @param action true if you want to show the card / set the retro of the card visible, false otherwise
   * @param rotation the rotation of the card
   */
  private def applyIcon(
    component: Component,
    indexInHand: Int,
    playerType: PlayerTypes,
    action: Boolean,
    rotation: Rotation
  ): Unit =
    player.get
      .lift(indexInHand)
      .foreach:
        card =>
          component match
            case cardCell: CardCell => if action then cardCell.applyCard(card) else cardCell.removeCard()
            case _ => component.asInstanceOf[Cell].setIcon(if action then rotateImage(retroCards, rotation) else null)

  /**
   * Apply the icon to the component understanding if it is a card or a cell
   *
   * @param component the component to apply the icon
   * @param indexInHand the index of the possible card in the hand of the player
   * @param playerType the type of the player
   * @param action true if you want to show the card / set the retro of the card visible, false otherwise
   */
  private def applyIcon(component: Component, indexInHand: Int, playerType: PlayerTypes, action: Boolean): Unit =
    applyIcon(component, indexInHand, playerType, action, NONE)

  override def paintComponent(g: Graphics): Unit =
    super.paintComponent(g)
    val g2d: Graphics2D = g.asInstanceOf[Graphics2D]
    g2d.drawImage(backgroundTable, 0, 0, getWidth, getHeight, this)
