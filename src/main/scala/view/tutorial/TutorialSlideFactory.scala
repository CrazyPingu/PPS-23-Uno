package view.tutorial

import utils.ImageHandler

/**
 * A factory class for creating tutorial slides.
 */
class TutorialSlideFactory:

  /**
   * Creates a slide that explains how to choose a card.
   *
   * @return The slide that explains how to choose a card.
   */
  def createCardSlide(): GeneralTutorialSlide =
    new GeneralTutorialSlide(
      ImageHandler.chooseCard,
      "How to choose a Card",
      "Click a card in your hand to play it when it's your turn"
    )

  /**
   * Creates a slide that explains how to draw a card.
   *
   * @return The slide that explains how to draw a card.
   */
  def createDeckSlide(): GeneralTutorialSlide =
    new GeneralTutorialSlide(
      ImageHandler.drawCard,
      "How to Draw a Card",
      "Draw a card from the deck if you can’t play any of your current cards"
    )

  /**
   * Creates a slide that explains how to play a card.
   *
   * @return The slide that explains how to play a card.
   */
  def createCompatibilitySlide(): GeneralTutorialSlide =
    new GeneralTutorialSlide(
      ImageHandler.cardCompatibility,
      "Card Compatibility",
      "Play cards that match the color or number of the top card on the discard pile"
    )

  /**
   * Creates a slide that explains how to call UNO.
   *
   * @return The slide that explains how to call UNO.
   */
  def createUnoSlide(): GeneralTutorialSlide =
    new GeneralTutorialSlide(
      ImageHandler.UnoPress,
      "How to call UNO",
      "Announce \"UNO\" when you have only one card left to avoid a penalty"
    )

  /**
   * Creates a slide that explains how to win a game.
   *
   * @return The slide that explains how to win a game.
   */
  def createWinLoseSlide(): GeneralTutorialSlide =
    new GeneralTutorialSlide(
      ImageHandler.tutorialWin,
      "How to Win a Game",
      "Be the first to play all your cards to win the game"
    )

  /**
   * Creates a slide that explains special cards.
   *
   * @return The slide that explains special cards.
   */
  def createSpecialCardSlide(): GeneralTutorialSlide =
    new GeneralTutorialSlide(
      ImageHandler.specialCards,
      "Special Cards",
      "Use special cards like Skip, Wild, Reverse, Draw +2 or +4 to alter the game and challenge your opponents"
    )
