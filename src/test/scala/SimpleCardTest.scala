import cards.SimpleCardImpl
import org.scalatest.funsuite.AnyFunSuite
import utils.Color

class SimpleCardTest extends AnyFunSuite:

  test("SimpleCardImpl should return correct number"):
    val card = new SimpleCardImpl(5, Color.Red)
    assert(card.getNumber == 5)


  test("SimpleCardImpl should return correct color"):
    val card = new SimpleCardImpl(5, Color.Red)
    assert(card.getColor == Color.Red)


  test("SimpleCardImpl toString should return correct string"):
    val card = new SimpleCardImpl(5, Color.Red)
    assert(card.toString == "Red 5")

