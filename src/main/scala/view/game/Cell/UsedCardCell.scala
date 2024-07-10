package view.game.Cell

import model.cards.Card
import utils.{Color, ImageHandler}

/**
 * The cell that represents the used card of the game
 */
class UsedCardCell extends Cell:

  /**
   * Dispose a card adding to the piles of disposed cards
   *
   * @param card the card to dispose
   */
  def disposeCard(card: Card): Unit = setIcon(card.image)
