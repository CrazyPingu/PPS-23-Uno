import model.Hand
import model.cards.SimpleCardImpl
import model.cards.special.ChangeColor
import org.scalatest.funsuite.AnyFunSuite
import utils.CardNumber.Five
import utils.{CardNumber, Color}
import utils.ImageHandler.loadCardImage

/**
 * Tests for a class that represents a hand of cards.
 */
class HandTest extends AnyFunSuite:

  final val N_CARDS: Int = 9

  test("The hand should start with zero cards"):
    val hand = new Hand()
    assert(hand.isEmpty)

  test("Should be able to add a SimpleCard"):
    val hand = new Hand()
    assert(hand.isEmpty)
    val simpleCard = new SimpleCardImpl(Five, Color.Red, loadCardImage("5", Color.Red))
    hand.addCard(simpleCard)
    assert(hand.size == 1)

  test("Should be able to add N SimpleCard"):
    val hand = new Hand()
    assert(hand.isEmpty)
    for num <- 0 to N_CARDS do
      val simpleCard = new SimpleCardImpl(CardNumber.values(num), Color.Red, loadCardImage(num.toString, Color.Red))
      hand.addCard(simpleCard)
      assert(hand.size == num + 1)

  test("Should be able to add a SpecialCard"):
    val hand = new Hand()
    assert(hand.isEmpty)
    val specialCard = new ChangeColor(null)
    hand.addCard(specialCard)
    assert(hand.size == 1)

  test("Should be able to add N SpecialCards"):
    val hand = new Hand()
    assert(hand.isEmpty)
    for num <- 0 to N_CARDS do
      val specialCard = new ChangeColor(null)
      hand.addCard(specialCard)
      assert(hand.size == num + 1)

  test("Should be able to remove a SimpleCard"):
    val hand = new Hand()
    assert(hand.isEmpty)
    val simpleCard = new SimpleCardImpl(Five, Color.Red, loadCardImage("5", Color.Red))
    hand.addCard(simpleCard)
    assert(hand.size == 1)
    hand.removeCard(simpleCard)
    assert(hand.isEmpty)

  test("Should be able to remove a SpecialCard"):
    val hand = new Hand()
    assert(hand.isEmpty)
    val specialCard = new ChangeColor(null)
    hand.addCard(specialCard)
    assert(hand.size == 1)
    hand.removeCard(specialCard)
    assert(hand.isEmpty)

  test("Should be able to empty the hand"):
    val hand = new Hand()
    assert(hand.isEmpty)
    for num <- 0 to N_CARDS do
      val specialCard = new ChangeColor(null)
      hand.addCard(specialCard)
      assert(hand.size == num + 1)
    hand.clearHand()
    assert(hand.isEmpty)

  test("Should be able to know how many cards the Hand has"):
    val hand = new Hand()
    assert(hand.isEmpty)
    for num <- 1 to N_CARDS do
      val specialCard = new ChangeColor(null)
      hand.addCard(specialCard)
      assert(hand.size == num)
    assert(hand.size == N_CARDS)

  test("Check if the Hand has a precise SimpleCard"):
    val hand = new Hand()
    assert(hand.isEmpty)
    val simpleCard = new SimpleCardImpl(Five, Color.Red, loadCardImage("5", Color.Red))
    hand.addCard(simpleCard)
    assert(hand.hasCard(simpleCard))

  test("Check if the Hand has a precise SpecialCard"):
    val hand = new Hand()
    assert(hand.isEmpty)
    val specialCard = new ChangeColor(null)
    hand.addCard(specialCard)
    assert(hand.hasCard(specialCard))

  test("Check if the Hand has a precise SpecialCard and a precise SimpleCard"):
    val hand = new Hand()
    assert(hand.isEmpty)
    val simpleCard = new SimpleCardImpl(Five, Color.Red, loadCardImage("5", Color.Red))
    val specialCard = new ChangeColor(null)
    hand.addCard(specialCard)
    hand.addCard(simpleCard)
    assert(hand.hasCard(specialCard))
    assert(hand.hasCard(simpleCard))
