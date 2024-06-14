package cards

import utils.Color

class SimpleCardImpl(private var num: Int, private var color: Color) extends SimpleCard:

  def getNumber: Int = num

  def getColor: Color = color

  override def toString: String = s"${color.toString} $num"