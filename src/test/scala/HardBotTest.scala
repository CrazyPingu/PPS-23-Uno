import org.scalatest.funsuite.AnyFunSuite
import model.bot.HardBotPlayerImpl
import model.cards.SimpleCard
import model.cards.SpecialCard.ChangeColor
import utils.Color
import utils.CardNumber.{Five, Seven, Six}

class HardBotTest extends AnyFunSuite:

  test("The BOT should start with empty hand"):
    val Bot = new HardBotPlayerImpl()
    assert(Bot.isEmpty)

  test("The BOT should choose the correct card"):
    val Bot = new HardBotPlayerImpl()
    Bot.addCard(SimpleCard(Five, Color.Red))
    val middleCard = SimpleCard(Five, Color.Blue)
    val card = Bot.chooseCardToUse(middleCard)
    assert(card.isDefined)
    Bot.addCard(SimpleCard(Five, Color.Red))
    val middleCard2 = SimpleCard(Five, Color.Red)
    val card2 = Bot.chooseCardToUse(middleCard2)
    assert(card.isDefined)

  test("The BOT should NOT choose and remove the card, because it's not compatible"):
    val Bot = new HardBotPlayerImpl()
    Bot.addCard(SimpleCard(Five, Color.Red))
    val middleCard = SimpleCard(Six, Color.Blue)
    val card = Bot.chooseCardToUse(middleCard)
    assert(card.isEmpty)

  test("The BOT should choose the Valid Simple Card"):
    val Bot = new HardBotPlayerImpl()
    Bot.addCard(SimpleCard(Five, Color.Red))
    Bot.addCard(SimpleCard(Five, Color.Blue))
    val middleCard = SimpleCard(Seven, Color.Red)
    val card = Bot.chooseCardToUse(middleCard)
    assert(card.get.color == Color.Red)
    assert(card.isDefined)

  test("The BOT should choose the Valid Special Card"):
    val Bot = new HardBotPlayerImpl()
    Bot.addCard(ChangeColor())
    Bot.addCard(SimpleCard(Five, Color.Blue))
    val middleCard = SimpleCard(Seven, Color.Red)
    val card = Bot.chooseCardToUse(middleCard)
    assert(card.get.color == Color.Black)
    assert(card.isDefined)

  test("The BOT should choose the Special Card"):
    val Bot = new HardBotPlayerImpl()
    Bot.addCard(ChangeColor())
    Bot.addCard(SimpleCard(Seven, Color.Red))
    val middleCard = SimpleCard(Seven, Color.Red)
    val card = Bot.chooseCardToUse(middleCard)
    assert(card.get.color == Color.Black)
    assert(card.isDefined)
