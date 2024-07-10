package view.game.Cell

import model.cards.Card

/**
 * A cell that contains a card
 */
class CardCell extends Cell:
  private var card: Option[Card] = None

  /**
   * Constructor of the cell with a card
   *
   * @param card the card to apply
   */
  def this(card: Card) =
    this()
    applyCard(card)

  /**
   * Apply a card to the cell
   *
   * @param card the card to apply
   */
  def applyCard(card: Card): Unit =
    this.card = Some(card)
    setIcon(card.image)

  /**
   * Remove the card from the cell
   */
  def removeCard(): Unit =
    this.card = None
    setIcon(null)

  addActionListener(
    _ =>
      if card.isEmpty then println("CardCell clicked! Empty")
      else println("CardCell clicked! " + card.get.toString)
  )
