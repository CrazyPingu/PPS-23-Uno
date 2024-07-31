package utils

/**
 * Represents the color of the card in the game.
 * Red, Green, Blue, Yellow are used as the original colors in the game.
 * Black is used as a special color for special cards.
 *
 * @param rgb The RGB value of the color
 */
enum Color(val rgb: Int):
  case Red extends Color(0xFF0000)
  case Green extends Color(0x00FF00)
  case Blue extends Color(0x0000FF)
  case Yellow extends Color(0xFFFF00)
  case Black extends Color(0x000000)


enum CardNumber(val value: Int):
  case Zero extends CardNumber(0)
  case One extends CardNumber(1)
  case Two extends CardNumber(2)
  case Three extends CardNumber(3)
  case Four extends CardNumber(4)
  case Five extends CardNumber(5)
  case Six extends CardNumber(6)
  case Seven extends CardNumber(7)
  case Eight extends CardNumber(8)
  case Nine extends CardNumber(9)
