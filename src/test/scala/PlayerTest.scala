import model.Player
import model.cards.SimpleCardImpl
import model.cards.SpecialCard.ChangeColor
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
    val player: Player = new Player()
    assert(player.isEmpty)

  test("Should be able to add a SimpleCard"):
    val player: Player = new Player()
    assert(player.isEmpty)
    val simpleCard = new SimpleCardImpl(Five, Color.Red, loadCardImage("5", Color.Red))
    player.addCard(simpleCard)
    assert(player.size == 1)

  test("Should be able to add N SimpleCard"):
    val player: Player = new Player()
    assert(player.isEmpty)
    for num <- 0 to N_CARDS do
      val simpleCard = new SimpleCardImpl(CardNumber.values(num), Color.Red, loadCardImage(num.toString, Color.Red))
      player.addCard(simpleCard)
      assert(player.size == num + 1)

  test("Should be able to add a SpecialCard"):
    val player: Player = new Player()
    assert(player.isEmpty)
    val specialCard = ChangeColor()
    player.addCard(specialCard)
    assert(player.size == 1)

  test("Should be able to add N SpecialCards"):
    val player: Player = new Player()
    assert(player.isEmpty)
    for num <- 0 to N_CARDS do
      val specialCard = ChangeColor()
      player.addCard(specialCard)
      assert(player.size == num + 1)

  test("Should be able to remove a SimpleCard"):
    val player: Player = new Player()
    assert(player.isEmpty)
    val simpleCard = new SimpleCardImpl(Five, Color.Red, loadCardImage("5", Color.Red))
    player.addCard(simpleCard)
    assert(player.size == 1)
    player.removeCard(simpleCard)
    assert(player.isEmpty)

  test("Should be able to remove a SpecialCard"):
    val player: Player = new Player()
    assert(player.isEmpty)
    val specialCard = ChangeColor()
    player.addCard(specialCard)
    assert(player.size == 1)
    player.removeCard(specialCard)
    assert(player.isEmpty)

  test("Should be able to empty the Player"):
    val player: Player = new Player()
    assert(player.isEmpty)
    for num <- 0 to N_CARDS do
      val specialCard = ChangeColor()
      player.addCard(specialCard)
      assert(player.size == num + 1)
    player.clearHand()
    assert(player.isEmpty)

  test("Should be able to know how many cards the Hand has"):
    val player: Player = new Player()
    assert(player.isEmpty)
    for num <- 1 to N_CARDS do
      val specialCard = ChangeColor()
      player.addCard(specialCard)
      assert(player.size == num)
    assert(player.size == N_CARDS)

  test("Check if the Hand has a precise SimpleCard"):
    val player: Player = new Player()
    assert(player.isEmpty)
    val simpleCard = new SimpleCardImpl(Five, Color.Red, loadCardImage("5", Color.Red))
    player.addCard(simpleCard)
    assert(player.hasCard(simpleCard))

  test("Check if the Player has a precise SpecialCard"):
    val player: Player = new Player()
    assert(player.isEmpty)
    val specialCard = ChangeColor()
    player.addCard(specialCard)
    assert(player.hasCard(specialCard))

  test("Check if the Hand has a precise SpecialCard and a precise SimpleCard"):
    val player: Player = new Player()
    assert(player.isEmpty)
    val simpleCard = new SimpleCardImpl(Five, Color.Red, loadCardImage("5", Color.Red))
    val specialCard = ChangeColor()
    player.addCard(specialCard)
    player.addCard(simpleCard)
    assert(player.hasCard(specialCard))
    assert(player.hasCard(simpleCard))
