import model.Player
import model.cards.SimpleCardImpl
import model.cards.special.ChangeColor
import org.scalatest.funsuite.AnyFunSuite
import utils.CardNumber.Five
import utils.{CardNumber, Color}
import utils.ImageHandler.loadCardImage

/**
 * Tests for a class that represents a Player of cards.
 */
class PlayerTest extends AnyFunSuite:

  final val N_CARDS: Int = 9

  test("The Player should start with zero cards"):
    assert(Player.isEmpty)

  test("Should be able to add a SimpleCard"):
    assert(Player.isEmpty)
    val simpleCard = new SimpleCardImpl(Five, Color.Red, loadCardImage("5", Color.Red))
    Player.addCard(simpleCard)
    assert(Player.size == 1)

  test("Should be able to add N SimpleCard"):
    assert(Player.isEmpty)
    for num <- 0 to N_CARDS do
      val simpleCard = new SimpleCardImpl(CardNumber.values(num), Color.Red, loadCardImage(num.toString, Color.Red))
      Player.addCard(simpleCard)
      assert(Player.size == num + 1)

  test("Should be able to add a SpecialCard"):
    assert(Player.isEmpty)
    val specialCard = new ChangeColor(null)
    Player.addCard(specialCard)
    assert(Player.size == 1)

  test("Should be able to add N SpecialCards"):
    assert(Player.isEmpty)
    for num <- 0 to N_CARDS do
      val specialCard = new ChangeColor(null)
      Player.addCard(specialCard)
      assert(Player.size == num + 1)

  test("Should be able to remove a SimpleCard"):
    assert(Player.isEmpty)
    val simpleCard = new SimpleCardImpl(Five, Color.Red, loadCardImage("5", Color.Red))
    Player.addCard(simpleCard)
    assert(Player.size == 1)
    Player.removeCard(simpleCard)
    assert(Player.isEmpty)

  test("Should be able to remove a SpecialCard"):
    assert(Player.isEmpty)
    val specialCard = new ChangeColor(null)
    Player.addCard(specialCard)
    assert(Player.size == 1)
    Player.removeCard(specialCard)
    assert(Player.isEmpty)

  test("Should be able to empty the Player"):
    assert(Player.isEmpty)
    for num <- 0 to N_CARDS do
      val specialCard = new ChangeColor(null)
      Player.addCard(specialCard)
      assert(Player.size == num + 1)
    Player.clearHand()
    assert(Player.isEmpty)

  test("Should be able to know how many cards the Hand has"):
    assert(Player.isEmpty)
    for num <- 1 to N_CARDS do
      val specialCard = new ChangeColor(null)
      Player.addCard(specialCard)
      assert(Player.size == num)
    assert(Player.size == N_CARDS)

  test("Check if the Hand has a precise SimpleCard"):
    assert(Player.isEmpty)
    val simpleCard = new SimpleCardImpl(Five, Color.Red, loadCardImage("5", Color.Red))
    Player.addCard(simpleCard)
    assert(Player.hasCard(simpleCard))

  test("Check if the Player has a precise SpecialCard"):
    assert(Player.isEmpty)
    val specialCard = new ChangeColor(null)
    Player.addCard(specialCard)
    assert(Player.hasCard(specialCard))

  test("Check if the Hand has a precise SpecialCard and a precise SimpleCard"):
    assert(Player.isEmpty)
    val simpleCard = new SimpleCardImpl(Five, Color.Red, loadCardImage("5", Color.Red))
    val specialCard = new ChangeColor(null)
    Player.addCard(specialCard)
    Player.addCard(simpleCard)
    assert(Player.hasCard(specialCard))
    assert(Player.hasCard(simpleCard))
