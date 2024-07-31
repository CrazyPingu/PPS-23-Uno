package model

import model.cards.Card
import model.cards.factory.CardFactory
import utils.{CardNumber, Color}

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

/**
 * Represents a deck of cards.
 * A deck is a list of cards; it contains 25 cards of each color (green, red, blue and yellow),
 * 19 number cards (1 zero and 2 of each number up to 9), 2 reverse cards, 2 skip cards, 2 draw 2 cards,
 * 4 wild draw 4 cards, 4 wild cards.
 */
class Deck extends ArrayBuffer[Card]:

  // Add colored cards to the deck
  for color <- Color.values if color != Color.Black do
    this += CardFactory.createSimpleCard(CardNumber.Zero, color)
    for _ <- 0 to 1 do
      this += CardFactory.createSkipCard(1, color)
      this += CardFactory.createReverseCard(color)
      this += CardFactory.createDrawCard(2, color)
    for number <- 0 to 17 do this += CardFactory.createSimpleCard(CardNumber.values(number / 2), color)

  // Add wild cards to the deck
  for _ <- 0 to 3 do
    this += CardFactory.createChangeColor()
    this += CardFactory.createDrawCard(4, Color.Black)

  this.shuffle()

  /**
   * Shuffles the deck.
   */
  private def shuffle(): Unit =
    val shuffledList = Random.shuffle(this)
    this.clear()
    this.addAll(shuffledList)

  /**
   * Draws a card from the deck.
   *
   * @return The card at the top of the deck.
   */
  def draw(): Card = this.remove(0)
