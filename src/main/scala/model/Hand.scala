package model

import model.cards.Card
import scala.collection.mutable.ArrayBuffer

/**
 * A class representing a hand of cards.
 */
class Hand extends ArrayBuffer[Card]:

  /**
   * Adds a card to the hand.
   *
   * @param card The card to add.
   */
  def addCard(card: Card): Unit = this += card

  /**
   * Removes a card from the hand, if it exists.
   *
   * @param card The card to remove.
   * @return `true` if the card was successfully removed, `false` otherwise.
   */
  def removeCard(card: Card): Boolean =
    if this.contains(card) then
      this -= card
      true
    else false

  /**
   * @return The number of cards in the hand.
   */
  def getCardCount: Int = this.size

  /**
   * Checks if the hand contains a specific card.
   *
   * @param card The card to check.
   * @return `true` if the hand contains the card, `false` otherwise.
   */
  def hasCard(card: Card): Boolean = this.contains(card)

  /**
   * Clears all cards from the hand.
   */
  def clearHand(): Unit = this.clear
