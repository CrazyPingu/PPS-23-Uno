package controller

import model.{Deck, Hand}
import view.game.Gui

class Controller(hand: Hand, deck: Deck):
  var gui: Option[Gui] = None

  def drawCard(): Unit =
    hand.addCard(deck.draw())
    gui.foreach(_.updateGui())

  def setGui(gui: Gui): Unit =
    this.gui = Some(gui)


