package view.game

import controller.GameLoop
import model.Player
import model.bot.BotPlayer
import model.cards.Card
import utils.ImageHandler.{backgroundTable, gameLogo, retroCards, rotateImage, turnArrow}
import utils.Rotation.{FLIP_HORIZONTAL, FLIP_VERTICAL, NONE, ROTATE_LEFT, ROTATE_RIGHT}
import utils.{ImageHandler, Rotation}
import view.game.Cell.{CardCell, Cell, DeckCell, DirectionCell, TurnIndicatorCell, UnoCell, UsedCardCell}
import view.game.GameGui.{
  arrowCoordinate,
  bot1,
  bot2,
  bot3,
  deckCoordinate,
  directionCell,
  directionCellCoordinate,
  gameLoop,
  panelGridDimension,
  player,
  turnCells,
  unoButton,
  unoCallCoordinate,
  usedCardCell,
  usedCardCoordinate
}

import java.awt.{Component, Graphics, Graphics2D, GridLayout}
import javax.swing.JPanel

/**
 * The graphical user interface of the game
 */
class GameGui private extends JPanel:
  setLayout(new GridLayout(panelGridDimension(1), panelGridDimension(0)))

  /**
   * Set the entity of the game and create the GUI
   *
   * @param bot1 the first bot
   * @param bot2 the second bot
   * @param bot3 the third bot
   */
  def setEntity(bot1: BotPlayer, bot2: BotPlayer, bot3: BotPlayer, player: Player): Unit =
    GameGui.bot1 = bot1
    GameGui.bot2 = bot2
    GameGui.bot3 = bot3
    GameGui.player = player
    createGui()

  /**
   * Create the GUI adding all the components
   */
  private def createGui(): Unit =
    this.removeAll()
    unoButton = UnoCell(gameLoop)
    val lastRow = GameGui.panelGridDimension(1) - 1
    val lastCol = panelGridDimension(0) - 1

    for row <- 0 until GameGui.panelGridDimension(1); col <- 0 until panelGridDimension(0) do
      val cell = (row, col) match
        case (r, c) if r == deckCoordinate(1) && c == deckCoordinate(0)                   => new DeckCell(gameLoop)
        case (r, c) if r == usedCardCoordinate(1) && c == usedCardCoordinate(0)           => usedCardCell
        case (r, c) if r == unoCallCoordinate(1) && c == unoCallCoordinate(0)             => unoButton
        case (r, c) if r == directionCellCoordinate(1) && c == directionCellCoordinate(0) => directionCell
        case (r, c) if arrowCoordinate.contains((r, c))                                   => turnCells((r, c))
        case (`lastRow`, c)                                                               => new CardCell(gameLoop)
        case _                                                                            => new Cell()
      this.add(cell)

    updateGui()
    this.validate()
    this.repaint()

  /**
   * Update the GUI in case of changes
   */
  def updateGui(): Unit =
    val lastRow = panelGridDimension(1) - 1
    val lastCol = panelGridDimension(0) - 1

    for i <- 0 until getComponentCount do
      val component = getComponent(i)

      (i / panelGridDimension(0), i % panelGridDimension(0)) match
//        Bot2 hand top row
        case (0, c) =>
          applyIcon(
            component,
            c - (panelGridDimension(0) - bot2.size) / 2,
            isWithinHand(c, (panelGridDimension(0) - bot2.size) / 2, bot2.size),
            FLIP_VERTICAL
          )

//        Player hand bottom row
        case (r, c) if r == lastRow => applyIcon(component, c - (panelGridDimension(0) - player.size) / 2)

//        Bot1 hand left column
        case (r, 0) => applyIconToColumns(component, bot1.size, r)

//        Bot3 hand right column
        case (r, `lastCol`) => applyIconToColumns(component, bot3.size, r)

        case _ => ()

    /**
     * Apply the icon to the columns of the bot
     *
     * @param component the component to apply the icon
     * @param handSize the size of the hand
     * @param indexRow the index of the row
     */
    def applyIconToColumns(component: Component, handSize: Int, indexRow: Int): Unit =
      applyIcon(
        component,
        indexRow - (panelGridDimension(1) - handSize) / 2,
        isWithinHand(indexRow, (panelGridDimension(1) - handSize) / 2, handSize),
        NONE
      )

    /**
     * Check if a position is within a hand
     *
     * @param position      the position to check
     * @param startPosition the start position of the hand
     * @return true if the position is within the hand, false otherwise
     */
    def isWithinHand(position: Int, startPosition: Int, hSize: Int): Boolean =
      position >= startPosition && position < startPosition + hSize

    /**
     * Apply the icon to the component understanding if it is a card or a cell
     *
     * @param component the component to apply the icon
     * @param indexInHand the index of the possible card in the hand of the player
     * @param action true if you want to show the card / set the retro of the card visible, false otherwise
     * @param rotation the rotation of the card
     */
    def applyIcon(component: Component, indexInHand: Int, action: Boolean = true, rotation: Rotation = NONE): Unit =
      component match
        case cell: CardCell =>
          player
            .lift(indexInHand) match
            case Some(card) => cell.applyCard(card)
            case None       => cell.removeCard()
        case cell: Cell if action => cell.setIcon(rotateImage(retroCards, rotation))
        case cell: Cell           => cell.setIcon(null)
        case _                    => ()

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
    getComponent(deckCoordinate(1) * panelGridDimension(0) + deckCoordinate(0)).setEnabled(toggle)
    for i <- 0 until panelGridDimension(0) do
      getComponent((panelGridDimension(1) - 1) * panelGridDimension(0) + i).setEnabled(toggle)

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
   * Set the uno button checked
   *
   * @param checked true to set the button checked, false otherwise
   */
  def setUnoButtonChecked(checked: Boolean): Unit = unoButton.setChecked(checked)

  /**
   * Update the turn arrow
   *
   * @param turn current turn
   * @param numOfPlayer number of player
   */
  def updateTurnArrow(turn: Int, numOfPlayer: Int = 4): Unit =
    for i <- arrowCoordinate.indices do
      val cell: TurnIndicatorCell = turnCells(arrowCoordinate(i))
      cell.setEnabled(i == turn % numOfPlayer)
      cell.validate()
      cell.repaint()

  override def paintComponent(g: Graphics): Unit =
    super.paintComponent(g)
    val g2d: Graphics2D = g.asInstanceOf[Graphics2D]
    g2d.drawImage(backgroundTable, 0, 0, getWidth, getHeight, this)

object GameGui:
  var gameLoop: GameLoop = _

  private val panelGridDimension: (Int, Int) = (27, 11)
  private val deckCoordinate: (Int, Int) = (panelGridDimension(0) / 2 - 2, panelGridDimension(1) / 2)
  private val usedCardCoordinate: (Int, Int) = (panelGridDimension(0) / 2 - 1, panelGridDimension(1) / 2)
  private val unoCallCoordinate: (Int, Int) = (panelGridDimension(0) / 2 + 1, panelGridDimension(1) / 2)
  private val directionCellCoordinate: (Int, Int) = (panelGridDimension(0) - 5, 2)
  private val arrowCoordinate: List[(Int, Int)] = List(
    (panelGridDimension(1) - 3, panelGridDimension(0) / 2),
    (panelGridDimension(1) / 2, 2),
    (2, panelGridDimension(0) / 2),
    (panelGridDimension(1) / 2, panelGridDimension(0) - 3)
  )

  private var unoButton: UnoCell = new UnoCell(gameLoop)
  private val directionCell: DirectionCell = new DirectionCell
  private val usedCardCell: UsedCardCell = new UsedCardCell

  private var bot1: BotPlayer = _
  private var bot2: BotPlayer = _
  private var bot3: BotPlayer = _
  private var player: Player = _

  private val turnCells: Map[(Int, Int), TurnIndicatorCell] = Map(
    (arrowCoordinate.head, new TurnIndicatorCell(ROTATE_RIGHT)), // Player
    (arrowCoordinate(1), new TurnIndicatorCell(FLIP_HORIZONTAL)), // Bot1
    (arrowCoordinate(2), new TurnIndicatorCell(ROTATE_LEFT)), // Bot2
    (arrowCoordinate(3), new TurnIndicatorCell(NONE)) // Bot3
  )

  def apply(): GameGui = new GameGui()
