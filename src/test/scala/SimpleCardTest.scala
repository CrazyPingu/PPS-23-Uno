import model.cards.SimpleCard
import org.scalatest.funsuite.AnyFunSuite
import utils.CardNumber.Five
import utils.Color

/**
 * Tests for the SimpleCardImpl class.
 */
class SimpleCardTest extends AnyFunSuite:

  test("SimpleCardImpl should return correct number"):
    val card = SimpleCard(Five, Color.Red)
    assert(card.num == Five)

  test("SimpleCardImpl should return correct color"):
    val card = SimpleCard(Five, Color.Red)
    assert(card.color == Color.Red)

  test("SimpleCardImpl toString should return correct string"):
    val card = SimpleCard(Five, Color.Red)
    assert(card.toString == "Red 5")
