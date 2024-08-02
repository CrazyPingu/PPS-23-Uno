package model.bot

import model.Hand
import model.cards.Card
import utils.Color

/**
 * A trait representing a bot player.
 */
trait BotPlayer extends Hand:


  /**
   * Chooses a card to play from the hand.
   *
   * @param card The card at the center of the table to be compared.
   * @return The valid card that the bot chooses to play, if present, `None` otherwise.
   */
  def chooseCardToUse(card: Card): Option[Card]

  /**
   * Finds the most frequent color in the hand.
   *
   * @return The color that is most present in the hand.
   */
  def chooseColor(): Color
