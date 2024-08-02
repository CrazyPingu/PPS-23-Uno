import model.bot.EasyBotPlayerImpl
import model.cards.SimpleCard
import model.cards.SpecialCard.ChangeColor
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
    Bot.addCard(SimpleCard(Five, Color.Red))
    val middleCard = SimpleCard(Five, Color.Blue)
    val card = Bot.chooseCardToUse(middleCard)
    assert(card.isDefined)
    Bot.addCard(SimpleCard(Five, Color.Red))
    val middleCard2 = SimpleCard(Five, Color.Red)
    val card2 = Bot.chooseCardToUse(middleCard2)
    assert(card.isDefined)

  test("The BOT should NOT choose and remove the card, because it's not compatible"):
    val Bot = new EasyBotPlayerImpl()
    Bot.addCard(SimpleCard(Five, Color.Red))
    val middleCard = SimpleCard(Six, Color.Blue)
    val card = Bot.chooseCardToUse(middleCard)
    assert(card.isEmpty)

  test("The BOT should choose the Valid Simple Card"):
    val Bot = new EasyBotPlayerImpl()
    Bot.addCard(SimpleCard(Five, Color.Red))
    Bot.addCard(SimpleCard(Six, Color.Blue))
    val middleCard = SimpleCard(Seven, Color.Red)
    val card = Bot.chooseCardToUse(middleCard)
    assert(card.get.color == Color.Red)
    assert(card.isDefined)

  test("The BOT should choose the Valid Special Card"):
    val Bot = new EasyBotPlayerImpl()
    Bot.addCard(ChangeColor())
    Bot.addCard(SimpleCard(Five, Color.Blue))
    val middleCard = SimpleCard(Seven, Color.Red)
    val card = Bot.chooseCardToUse(middleCard)
    assert(card.get.color == Color.Black)
    assert(card.isDefined)
