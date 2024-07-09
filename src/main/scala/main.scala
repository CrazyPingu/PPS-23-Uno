import controller.Controller
import model.{Deck, Hand}
import utils.PlayerTypes
import view.game.Gui

object main:

  def main(args: Array[String]): Unit =

    val deck = new Deck()
//    val hands = Array(new Hand, new Hand, new Hand, new Hand)

    val pHand = new Hand
    val hands = Map(
      PlayerTypes.Player -> pHand,
      PlayerTypes.Bot1 -> new Hand,
      PlayerTypes.Bot2 -> new Hand,
      PlayerTypes.Bot3 -> new Hand
    )


    for _ <- 0 to 2 do
      for hand <- hands.values do
        hand.addCard(deck.draw())

    val controller = new Controller(pHand, deck)

    val gui = new Gui(controller, hands)

    controller.setGui(gui)



