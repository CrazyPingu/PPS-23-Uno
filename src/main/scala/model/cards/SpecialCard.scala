package model.cards

import controller.GameController
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

  /**
   * Represents a change color card.
   */
  case class ChangeColor(override val color: Color = Color.Black)
      extends SpecialCard(color, loadCardImage("Wild", color)):
    override def toString: String = "ChangeColor " + color.toString

    /**
     * Changes the color of the card that is being played.
     */
    override def execute(): Unit = GameController.showChangeColor()

    /**
     * Represents a reverse card.
     *
     * @param color The color of the card
     */
  case class ReverseCard(override val color: Color) extends SpecialCard(color, loadCardImage("Reverse", color)):
    override def toString: String = "Reverse " + color.toString

    /**
     * Reverses the turn order.
     */
    override def execute(): Unit = GameController.reverseDirection()

    /**
     * Represents a skip turn card.
     */
  case class SkipCard(override val color: Color, numberToSkip: Int = 1)
      extends SpecialCard(color, loadCardImage("Skip", color)):

    override def toString: String = "Skip " + color.toString

    /**
     * The next player has to skip a certain number of turns.
     */
    override def execute(): Unit = GameController.skipNextTurn(numberToSkip)

  /**
   * Represents a wild draw four card.
   */
  case class WildDrawFourCard(override val color: Color = Color.Black) extends SpecialCard(color, loadCardImage("Draw4", color)):

    override def toString: String = "Draw 4" + " " + color.toString

    /**
     * The next player has to draw a certain number of cards from the deck.
     */
    override def execute(): Unit = GameController.nextDrawCard(4)

  case class DrawTwoCard(override val color: Color) extends SpecialCard(color, loadCardImage("Draw2", color)):

    override def toString: String = "Draw 2" + " " + color.toString

    /**
     * The next player has to draw a certain number of cards from the deck.
     */
    override def execute(): Unit = GameController.nextDrawCard(2)
