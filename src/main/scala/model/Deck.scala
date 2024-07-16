package model

import model.cards.Card
import model.cards.factory.CardFactoryImpl
import utils.Color

import scala.collection.mutable.ArrayBuffer
import scala.util.Random


/**
 * Represents a deck of cards.
 * A deck is a list of cards; it contains 25 cards of each color (green, red, blue and yellow),
 * 19 number cards (1 zero and 2 of each number up to 9), 2 reverse cards, 2 skip cards, 2 draw 2 cards,
 * 4 wild draw 4 cards, 4 wild cards.
 */
class Deck extends ArrayBuffer[Card]:

  private final val factory: CardFactoryImpl = new CardFactoryImpl

  // Add colored cards to the deck
  for color <- Color.values if color != Color.Black do
    this += factory.createSimpleCard(0, color)
    for _ <- 0 to 1 do
      this += factory.createSkipCard(1, color)
      this += factory.createReverseCard(color)
      this += factory.createDrawCard(2, color)
    for number <- 0 to 17 do
      this += factory.createSimpleCard(number / 2 + 1, color)

  // Add wild cards to the deck
  for _ <- 0 to 3 do
    this += factory.createChangeColor()
    this += factory.createDrawCard(4, Color.Black)

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
