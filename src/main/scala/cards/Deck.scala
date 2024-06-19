package cards

import cards.special.{ChangeColor, DrawCard, ReverseCard, SkipCard}
import utils.Color
import utils.ImageHandler.loadCardImage
import java.util
import scala.jdk.CollectionConverters.*
import scala.util.Random


/**
 * Represents a deck of cards.
 * A deck is a list of cards; it contains 25 cards of each color (green, red, blue and yellow),
 * 19 number cards (1 zero and 2 of each number up to 9), 2 reverse cards, 2 skip cards, 2 draw 2 cards,
 * 4 wild draw 4 cards, 4 wild cards.
 */
class Deck extends util.ArrayList[Card]:

  // Add colored cards to the deck
  for color <- Color.values if color != Color.Black do
    this.add(new SimpleCardImpl(0, color, loadCardImage("0", color)))
    for _ <- 0 to 1 do
      this.add(new SkipCard(color, 1))
      this.add(new ReverseCard(color))
      this.add(new DrawCard(color, 2))
    for number <- 0 to 17 do
      this.add(new SimpleCardImpl(number / 2 + 1, color, loadCardImage((number / 2 + 1).toString, color)))

  // Add wild cards to the deck
  for _ <- 0 to 3 do
    this.add(new ChangeColor())
    this.add(new DrawCard(Color.Black, 4))

  this.shuffle()

  /**
   * Shuffles the deck.
   */
  def shuffle(): Unit =
    val shuffledList = Random.shuffle(this.asScala)
    this.clear()
    this.addAll(shuffledList.asJava)

  /**
   * Draws a card from the deck.
   *
   * @return The card at the top of the deck.
   */
  def draw(): Card = this.remove(0)
