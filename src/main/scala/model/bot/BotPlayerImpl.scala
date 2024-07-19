package model.bot

import model.cards.{Card, SimpleCard, SpecialCard}
import utils.{Color, Compatibility}

import scala.collection.mutable
import scala.util.Random

abstract class BotPlayerImpl extends BotPlayer:
  protected def isCompatible(selectedCard: Card, centerCard: Card): Boolean =
    Compatibility.isCompatible(selectedCard, centerCard)
  
  def chooseColor(): Color =
    val colorCounts = mutable.Map[Color, Int]().withDefaultValue(0)

    for card <- this do colorCounts(card.color) += 1

    if colorCounts.isEmpty then return Random.shuffle(Color.values.filterNot(_ == Color.Black).toList).head

    val sortedColors = colorCounts.toSeq.sortBy(-_._2)
    val mostFrequent = sortedColors.head._1

    if mostFrequent == Color.Black && sortedColors.size > 1 then sortedColors(1)._1
    else mostFrequent
