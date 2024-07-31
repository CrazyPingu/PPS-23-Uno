import model.Deck
import model.cards.SimpleCardImpl
import model.cards.special.{ChangeColor, DrawCard, ReverseCard, SkipCard}
import org.scalatest.funsuite.AnyFunSuite
import utils.Color

/**
 * Tests for the Deck class.
 */
class DeckTest extends AnyFunSuite:

  test("Deck should be initialized with 108 cards"):
    assert(Deck.size == 108)

  test("Deck should contain 19 number cards of each color"):
    val colors = Seq(Color.Red, Color.Green, Color.Blue, Color.Yellow)
    for color <- colors do
      val numberCards = Deck.collect:
        case card: SimpleCardImpl if card.color == color => card
      assert(numberCards.size == 19)

  test("Deck should contain 2 reverse cards of each color"):
    val colors = Seq(Color.Red, Color.Green, Color.Blue, Color.Yellow)
    for color <- colors do
      val reverseCards = Deck.collect:
        case card: ReverseCard if card.color == color => card
      assert(reverseCards.size == 2)

  test("Deck should contain 2 skip cards of each color"):
    val colors = Seq(Color.Red, Color.Green, Color.Blue, Color.Yellow)
    for color <- colors do
      val skipCards = Deck.collect:
        case card: SkipCard if card.color == color => card
      assert(skipCards.size == 2)

  test("Deck should contain 2 draw 2 cards of each color"):
    val colors = Seq(Color.Red, Color.Green, Color.Blue, Color.Yellow)
    for color <- colors do
      val drawCards = Deck.collect:
        case card: DrawCard if card.color == color && card.numberToDraw == 2 => card
      assert(drawCards.size == 2)

  test("Deck should contain 4 wild draw 4 cards"):
    val wildDrawCards = Deck.collect:
      case card: DrawCard if card.color == Color.Black && card.numberToDraw == 4 => card
    assert(wildDrawCards.size == 4)

  test("Deck should contain 4 change color cards"):
    val changeColorCards = Deck.collect:
      case card: ChangeColor => card
    assert(changeColorCards.size == 4)

  test("Deck should draw a card from the top"):
    val firstCard = Deck(0)
    val drawnCard = Deck.draw()
    assert(drawnCard == firstCard)
    assert(Deck.size == 107)
