import org.scalatest.funsuite.AnyFunSuite
import model.bot.HardBotPlayerImpl
import model.cards.special.ChangeColor
import model.cards.{SimpleCardImpl, SpecialCard}
import utils.Color
import utils.ImageHandler.loadCardImage

class HardBotTest extends AnyFunSuite:

  test("The BOT should start with empty hand"):
    val Bot = new HardBotPlayerImpl()
    assert(Bot.isEmpty)

  test("The BOT should choose and remove the cards"):
    val Bot = new HardBotPlayerImpl()
    Bot.addCard(new SimpleCardImpl(5, Color.Red, loadCardImage("5", Color.Red)))
    val middleCard = new SimpleCardImpl(5, Color.Blue, loadCardImage("5", Color.Blue))
    val card = Bot.chooseCardToUse(middleCard)
    assert(Bot.isEmpty)
    assert(card.isDefined)
    Bot.addCard(new SimpleCardImpl(5, Color.Red, loadCardImage("5", Color.Red)))
    val middleCard2 = new SimpleCardImpl(5, Color.Red, loadCardImage("5", Color.Red))
    val card2 = Bot.chooseCardToUse(middleCard2)
    assert(Bot.isEmpty)
    assert(card.isDefined)

  test("The BOT should NOT choose and remove the card, because it's not compatible"):
    val Bot = new HardBotPlayerImpl()
    Bot.addCard(new SimpleCardImpl(5, Color.Red, loadCardImage("5", Color.Red)))
    val middleCard = new SimpleCardImpl(6, Color.Blue, loadCardImage("6", Color.Blue))
    val card = Bot.chooseCardToUse(middleCard)
    assert(card.isEmpty)

  test("The BOT should choose the Valid Simple Card and remove it"):
    val Bot = new HardBotPlayerImpl()
    Bot.addCard(new SimpleCardImpl(5, Color.Red, loadCardImage("5", Color.Red)))
    Bot.addCard(new SimpleCardImpl(5, Color.Blue, loadCardImage("6", Color.Blue)))
    val middleCard = new SimpleCardImpl(7, Color.Red, loadCardImage("7", Color.Red))
    val card = Bot.chooseCardToUse(middleCard)
    assert(Bot.head.color == Color.Blue)
    assert(card.isDefined)
    assert(Bot.size == 1)

  test("The BOT should choose the Valid Special Card and remove it"):
    val Bot = new HardBotPlayerImpl()
    Bot.addCard(new ChangeColor())
    Bot.addCard(new SimpleCardImpl(5, Color.Blue, loadCardImage("6", Color.Blue)))
    val middleCard = new SimpleCardImpl(7, Color.Red, loadCardImage("7", Color.Red))
    val card = Bot.chooseCardToUse(middleCard)
    assert(Bot.head.color == Color.Blue)
    assert(card.isDefined)
    assert(Bot.size == 1)


  test("The BOT should choose the Special Card and remove it"):
    val Bot = new HardBotPlayerImpl()
    Bot.addCard(new ChangeColor())
    Bot.addCard(new SimpleCardImpl(7, Color.Red, loadCardImage("7", Color.Red)))
    val middleCard = new SimpleCardImpl(7, Color.Red, loadCardImage("7", Color.Red))
    val card = Bot.chooseCardToUse(middleCard)
    assert(Bot.head.color == Color.Red)
    assert(card.isDefined)
    assert(Bot.size == 1)