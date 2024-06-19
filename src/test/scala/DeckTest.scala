import cards.special.{ChangeColor, DrawCard, ReverseCard, SkipCard}
import cards.{Deck, SimpleCardImpl}
import org.scalatest.funsuite.AnyFunSuite
import utils.Color

import scala.jdk.CollectionConverters.*

/**
 * Tests for the Deck class.
 */
class DeckTest extends AnyFunSuite:

  test("Deck should be initialized with 108 cards"):
    val deck = new Deck()
    assert(deck.size() == 108)

  test("Deck should contain 19 number cards of each color"):
    val deck = new Deck()
    val colors = Seq(Color.Red, Color.Green, Color.Blue, Color.Yellow)
    for color <- colors do
      val numberCards = deck.asScala.collect:
        case card: SimpleCardImpl if card.getColor == color => card
      assert(numberCards.size == 19)


  test("Deck should contain 2 reverse cards of each color"):
    val deck = new Deck()
    val colors = Seq(Color.Red, Color.Green, Color.Blue, Color.Yellow)
    for color <- colors do
      val reverseCards = deck.asScala.collect:
        case card: ReverseCard if card.getColor == color => card
      assert(reverseCards.size == 2)


  test("Deck should contain 2 skip cards of each color"):
    val deck = new Deck()
    val colors = Seq(Color.Red, Color.Green, Color.Blue, Color.Yellow)
    for color <- colors do
      val skipCards = deck.asScala.collect:
        case card: SkipCard if card.getColor == color => card
      assert(skipCards.size == 2)


  test("Deck should contain 2 draw 2 cards of each color"):
    val deck = new Deck()
    val colors = Seq(Color.Red, Color.Green, Color.Blue, Color.Yellow)
    for color <- colors do
      val drawCards = deck.asScala.collect:
        case card: DrawCard if card.getColor == color && card.getNumberToDraw == 2 => card
      assert(drawCards.size == 2)


  test("Deck should contain 4 wild draw 4 cards"):
    val deck = new Deck()
    val wildDrawCards = deck.asScala.collect:
      case card: DrawCard if card.getColor == Color.Black && card.getNumberToDraw == 4 => card
    assert(wildDrawCards.size == 4)


  test("Deck should contain 4 change color cards"):
    val deck = new Deck()
    val changeColorCards = deck.asScala.collect:
      case card: ChangeColor => card
    assert(changeColorCards.size == 4)


  test("Deck should shuffle properly"):
    val deck = new Deck()
    val originalOrder = deck.asScala.toList
    deck.shuffle()
    val shuffledOrder = deck.asScala.toList
    assert(originalOrder != shuffledOrder)


  test("Deck should draw a card from the top"):
    val deck = new Deck()
    val firstCard = deck.get(0)
    val drawnCard = deck.draw()
    assert(drawnCard == firstCard)
    assert(deck.size() == 107)


