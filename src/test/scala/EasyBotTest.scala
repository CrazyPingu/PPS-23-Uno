import model.bot.EasyBotPlayerImpl
import model.cards.special.ChangeColor
import model.cards.{SimpleCardImpl, SpecialCard}
import org.scalatest.funsuite.AnyFunSuite
import utils.Color
import utils.ImageHandler.loadCardImage

class EasyBotTest extends AnyFunSuite:

  test("The BOT should start with empty hand"):
    val Bot = new EasyBotPlayerImpl()
    assert(Bot.isEmpty)

  test("The BOT should choose and remove the cards"):
    val Bot = new EasyBotPlayerImpl()
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
    val Bot = new EasyBotPlayerImpl()
    Bot.addCard(new SimpleCardImpl(5, Color.Red, loadCardImage("5", Color.Red)))
    val middleCard = new SimpleCardImpl(6, Color.Blue, loadCardImage("6", Color.Blue))
    val card = Bot.chooseCardToUse(middleCard)
    assert(card.isEmpty)

  test("The BOT should choose the Valid Simple Card and remove it"):
    val Bot = new EasyBotPlayerImpl()
    Bot.addCard(new SimpleCardImpl(5, Color.Red, loadCardImage("5", Color.Red)))
    Bot.addCard(new SimpleCardImpl(5, Color.Blue, loadCardImage("6", Color.Blue)))
    val middleCard = new SimpleCardImpl(7, Color.Red, loadCardImage("7", Color.Red))
    val card = Bot.chooseCardToUse(middleCard)
    assert(Bot.head.color == Color.Blue)
    assert(card.isDefined)
    assert(Bot.size == 1)

  test("The BOT should choose the Valid Special Card and remove it"):
    val Bot = new EasyBotPlayerImpl()
    Bot.addCard(new ChangeColor(null))
    Bot.addCard(new SimpleCardImpl(5, Color.Blue, loadCardImage("6", Color.Blue)))
    val middleCard = new SimpleCardImpl(7, Color.Red, loadCardImage("7", Color.Red))
    val card = Bot.chooseCardToUse(middleCard)
    assert(Bot.head.color == Color.Blue)
    assert(card.isDefined)
    assert(Bot.size == 1)