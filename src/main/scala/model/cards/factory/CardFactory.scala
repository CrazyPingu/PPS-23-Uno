package model.cards.factory

import model.cards.Card
import utils.Color

/**
 * Factory for creating cards.
 */
trait CardFactory:
  
  /**
   * Creates a simple card with the given number and color.
   *
   * @param num   The number of the card.
   * @param color The color of the card.
   * @return The created card.
   */
  def createSimpleCard(num: Int, color: Color): Card

  /**
   * @return The created change color card.
   */
  def createChangeColor(): Card

  /**
   * Creates a draw card with the given number of cards to draw.
   *
   * @param numToDraw The number of cards to draw.
   * @return The created draw card.
   */
  def createDrawCard(numToDraw: Int, color: Color): Card

  /**
   * Creates a reverse card with the given color.
   *
   * @param color The color of the card.
   * @return The created reverse card.
   */
  def createReverseCard(color: Color): Card

  /**
   * Creates a skip card with the given color.
   *
   * @param turnToSkip The number of turns to skip.
   * @param color The color of the card.
   * @return The created skip card.
   */
  def createSkipCard(turnToSkip: Int, color: Color): Card


