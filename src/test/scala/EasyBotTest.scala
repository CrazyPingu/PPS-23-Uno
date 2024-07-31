import model.bot.EasyBotPlayerImpl
import model.cards.special.ChangeColor
import model.cards.SimpleCardImpl
import org.scalatest.funsuite.AnyFunSuite
import utils.CardNumber.{Five, Seven, Six}
import utils.Color
import utils.ImageHandler.loadCardImage

class EasyBotTest extends AnyFunSuite:

  test("The BOT should start with empty hand"):
    val Bot = new EasyBotPlayerImpl()
    assert(Bot.isEmpty)

  test("The BOT should choose the card"):
    val Bot = new EasyBotPlayerImpl()
    Bot.addCard(new SimpleCardImpl(Five, Color.Red, loadCardImage("5", Color.Red)))
    val middleCard = new SimpleCardImpl(Five, Color.Blue, loadCardImage("5", Color.Blue))
    val card = Bot.chooseCardToUse(middleCard)
    assert(card.isDefined)
    Bot.addCard(new SimpleCardImpl(Five, Color.Red, loadCardImage("5", Color.Red)))
    val middleCard2 = new SimpleCardImpl(Five, Color.Red, loadCardImage("5", Color.Red))
    val card2 = Bot.chooseCardToUse(middleCard2)
    assert(card.isDefined)

  test("The BOT should NOT choose and remove the card, because it's not compatible"):
    val Bot = new EasyBotPlayerImpl()
    Bot.addCard(new SimpleCardImpl(Five, Color.Red, loadCardImage("5", Color.Red)))
    val middleCard = new SimpleCardImpl(Six, Color.Blue, loadCardImage("6", Color.Blue))
    val card = Bot.chooseCardToUse(middleCard)
    assert(card.isEmpty)

  test("The BOT should choose the Valid Simple Card"):
    val Bot = new EasyBotPlayerImpl()
    Bot.addCard(new SimpleCardImpl(Five, Color.Red, loadCardImage("5", Color.Red)))
    Bot.addCard(new SimpleCardImpl(Six, Color.Blue, loadCardImage("6", Color.Blue)))
    val middleCard = new SimpleCardImpl(Seven, Color.Red, loadCardImage("7", Color.Red))
    val card = Bot.chooseCardToUse(middleCard)
    assert(card.get.color == Color.Red)
    assert(card.isDefined)

  test("The BOT should choose the Valid Special Card"):
    val Bot = new EasyBotPlayerImpl()
    Bot.addCard(new ChangeColor(null))
    Bot.addCard(new SimpleCardImpl(Five, Color.Blue, loadCardImage("6", Color.Blue)))
    val middleCard = new SimpleCardImpl(Seven, Color.Red, loadCardImage("7", Color.Red))
    val card = Bot.chooseCardToUse(middleCard)
    assert(card.get.color == Color.Black)
    assert(card.isDefined)
