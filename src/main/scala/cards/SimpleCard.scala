package cards

/**
 * A simple card that has a number.
 */
trait SimpleCard extends Card:
  def number: Int
  /**
   * @return The number of the card
   */
  def getNumber: Int = number

