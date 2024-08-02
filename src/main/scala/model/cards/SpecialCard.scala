package model.cards

import controller.GameLoop
import utils.Color
import utils.ImageHandler.loadCardImage

import java.awt.Image

/**
 * Represents a special card in the game.
 *
 * @param color The color of the card
 * @param image The image of the card
 */
abstract class SpecialCard(val color: Color, val image: Image) extends Card:

  /**
   * Executes the effect of the card.
   */
  def execute(): Unit

object SpecialCard:
  private var _gameLoop: Option[GameLoop] = None

  private def gameLoop: GameLoop = _gameLoop.getOrElse(throw new IllegalStateException("GameLoop is not initialized"))

  def setGameLoop(loop: GameLoop): Unit = _gameLoop = Some(loop)

  /**
   * Represents a change color card.
   */
  case class ChangeColor(override val color: Color = Color.Black)
    extends SpecialCard(color, loadCardImage("Wild", color)):
    override def toString: String = s"ChangeColor $color"

    /**
     * Changes the color of the card that is being played.
     */
    override def execute(): Unit = gameLoop.showChangeColor()

  /**
   * Represents a reverse card.
   *
   * @param color The color of the card
   */
  case class ReverseCard(override val color: Color)
    extends SpecialCard(color, loadCardImage("Reverse", color)):
    override def toString: String = s"Reverse $color"

    /**
     * Reverses the turn order.
     */
    override def execute(): Unit = gameLoop.reverseTurnOrder()

  /**
   * Represents a skip turn card.
   */
  case class SkipCard(override val color: Color, numberToSkip: Int = 1)
    extends SpecialCard(color, loadCardImage("Skip", color)):
    override def toString: String = s"Skip $color"

    /**
     * The next player has to skip a certain number of turns.
     */
    override def execute(): Unit = gameLoop.skipNextTurn(numberToSkip)

  /**
   * Represents a wild draw four card.
   */
  case class WildDrawFourCard(override val color: Color = Color.Black)
    extends SpecialCard(color, loadCardImage("Draw4", color)):
    override def toString: String = s"Draw 4 $color"

    /**
     * The next player has to draw a certain number of cards from the deck.
     */
    override def execute(): Unit = gameLoop.nextDrawCard(4)

  /**
   * Represents a draw two card.
   */
  case class DrawTwoCard(override val color: Color)
    extends SpecialCard(color, loadCardImage("Draw2", color)):
    override def toString: String = s"Draw 2 $color"

    /**
     * The next player has to draw a certain number of cards from the deck.
     */
    override def execute(): Unit = gameLoop.nextDrawCard(2)
