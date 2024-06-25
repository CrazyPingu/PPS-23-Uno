package utils

enum ComparisonOperator:
  case Greater
  case Less
  case Equal
  
  def compare(x: Int, y: Int): Boolean = this match
    case Greater => x > y
    case Less => x < y
    case Equal => x == y