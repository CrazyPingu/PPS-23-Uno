import cards.SimpleCardImpl
import org.scalatest.funsuite.AnyFunSuite
import utils.Color
import utils.ImageHandler.loadCardImage

/**
 * Tests for the SimpleCardImpl class.
 */
class SimpleCardTest extends AnyFunSuite:

  test("SimpleCardImpl should return correct number"):
    val card = new SimpleCardImpl(5, Color.Red, loadCardImage("5", Color.Red))
    assert(card.getNumber == 5)


  test("SimpleCardImpl should return correct color"):
    val card = new SimpleCardImpl(5, Color.Red, loadCardImage("5", Color.Red))
    assert(card.getColor == Color.Red)


  test("SimpleCardImpl toString should return correct string"):
    val card = new SimpleCardImpl(5, Color.Red, loadCardImage("5", Color.Red))
    assert(card.toString == "Red 5")

