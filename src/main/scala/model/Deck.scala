package model

import model.cards.SpecialCard.{ChangeColor, DrawTwoCard, ReverseCard, SkipCard, WildDrawFourCard}
import model.cards.{Card, SimpleCard}
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

  for color <- Color.values if color != Color.Black do
    this += SimpleCard(CardNumber.Zero, color)
    for _ <- 0 to 1 do
      this += SkipCard(color)
      this += ReverseCard(color)
      this += DrawTwoCard(color)
    for number <- 0 to 17 do this += SimpleCard(CardNumber.values(number / 2), color)

  for _ <- 0 to 3 do
    this += ChangeColor()
    this += WildDrawFourCard()

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
