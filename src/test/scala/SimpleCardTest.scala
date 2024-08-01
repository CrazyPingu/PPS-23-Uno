import model.cards.SimpleCardImpl
import org.scalatest.funsuite.AnyFunSuite
import utils.CardNumber.Five
import utils.Color
import utils.ImageHandler.loadCardImage

/**
 * Tests for the SimpleCardImpl class.
 */
class SimpleCardTest extends AnyFunSuite:

  test("SimpleCardImpl should return correct number"):
    val card = new SimpleCardImpl(Five, Color.Red, loadCardImage("5", Color.Red))
    assert(card.num == Five)

  test("SimpleCardImpl should return correct color"):
    val card = new SimpleCardImpl(Five, Color.Red, loadCardImage("5", Color.Red))
    assert(card.color == Color.Red)

  test("SimpleCardImpl toString should return correct string"):
    val card = new SimpleCardImpl(Five, Color.Red, loadCardImage("5", Color.Red))
    assert(card.toString == "Red 5")
