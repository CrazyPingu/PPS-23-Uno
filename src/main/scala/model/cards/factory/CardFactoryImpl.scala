package model.cards.factory

import model.cards.special.{ChangeColor, DrawCard, ReverseCard, SkipCard}
import model.cards.{Card, SimpleCardImpl}
import utils.Color
import utils.ImageHandler.loadCardImage

/**
 * Factory for creating cards.
 */
class CardFactoryImpl extends CardFactory:

  def createSimpleCard(num: Int, color: Color): Card = new SimpleCardImpl(num, color, loadCardImage(num.toString, color))

  def createChangeColor(): Card = new ChangeColor()

  def createDrawCard(numToDraw: Int, color: Color): Card = new DrawCard(color, numToDraw)

  def createReverseCard(color: Color): Card = new ReverseCard(color)

  def createSkipCard(turnToSkip: Int, color: Color): Card = new SkipCard(color, turnToSkip)