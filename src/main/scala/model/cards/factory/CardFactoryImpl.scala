package model.cards.factory

import controller.GameController
import model.cards.special.{ChangeColor, DrawCard, ReverseCard, SkipCard}
import model.cards.{Card, SimpleCardImpl}
import utils.Color
import utils.ImageHandler.loadCardImage

/**
 * Factory for creating cards.
 */
class CardFactoryImpl extends CardFactory:
  private var controller: GameController = _

  def createSimpleCard(num: Int, color: Color): Card =
    new SimpleCardImpl(num, color, loadCardImage(num.toString, color))

  def createChangeColor(color: Color = Color.Black): Card = new ChangeColor(controller, color)

  def createDrawCard(numToDraw: Int, color: Color): Card = new DrawCard(color, numToDraw, controller)

  def createReverseCard(color: Color): Card = new ReverseCard(color, controller)

  def createSkipCard(turnToSkip: Int, color: Color): Card = new SkipCard(color, turnToSkip, controller)

  def attachController(controller: GameController): CardFactoryImpl =
    this.controller = controller
    this
