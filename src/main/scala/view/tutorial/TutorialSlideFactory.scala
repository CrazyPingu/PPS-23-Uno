package view.tutorial

import utils.ImageHandler

class TutorialSlideFactory:

  def createSlides(): Array[GeneralTutorialSlide] =
    Array(
      createCompatibilitySlide(),
      createSpecialCardSlide(),
      createCardSlide(),
      createDeckSlide(),
      createUnoSlide(),
      createWinLoseSlide()
    )

  private def createCardSlide(): GeneralTutorialSlide =
    new GeneralTutorialSlide(ImageHandler.chooseCard, "How to choose a Card", "Click a card in your hand to play it when it's your turn")

  private def createDeckSlide(): GeneralTutorialSlide =
    new GeneralTutorialSlide(ImageHandler.drawCard, "How to Draw a Card", "Draw a card from the deck if you can’t play any of your current cards")

  private def createCompatibilitySlide(): GeneralTutorialSlide =
    new GeneralTutorialSlide(ImageHandler.cardCompatibility, "Card Compatibility", "Play cards that match the color or number of the top card on the discard pile")

  private def createUnoSlide(): GeneralTutorialSlide =
    new GeneralTutorialSlide(ImageHandler.UnoPress, "How to call UNO", "Announce \"UNO\" when you have only one card left to avoid a penalty")

  private def createWinLoseSlide(): GeneralTutorialSlide =
    new GeneralTutorialSlide(ImageHandler.tutorialWin, "How to Win a Game", "Be the first to play all your cards to win the game")

  private def createSpecialCardSlide(): GeneralTutorialSlide =
    new GeneralTutorialSlide(ImageHandler.specialCards, "Special Cards", "Use special cards like Skip, Wild, Reverse, Draw +2 or +4 to alter the game and challenge your opponents")