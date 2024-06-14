package utils

enum Color(val rgb: Int):
  case Red extends Color(0xFF0000)
  case Green extends Color(0x00FF00)
  case Blue extends Color(0x0000FF)
  case Yellow extends Color(0xFFFF00)
  case Black extends Color(0x000000)