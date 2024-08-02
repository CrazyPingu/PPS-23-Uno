package model.bot

import model.cards.Card
import utils.{Color, Compatibility}

import scala.collection.mutable
import scala.util.Random

abstract class BotPlayerImpl extends BotPlayer:
  protected def isCompatible(selectedCard: Card, centerCard: Card): Boolean =
    Compatibility.isCompatible(selectedCard, centerCard)

  override def chooseColor(): Color =
    val colorCounts = mutable.Map[Color, Int]().withDefaultValue(0)

    for card <- this do colorCounts(card.color) += 1

    if colorCounts.isEmpty then return Random.shuffle(Color.values.filterNot(_ == Color.Black).toList).head

    val sortedColors = colorCounts.toSeq.sortBy(-_(1))
    val mostFrequent = sortedColors.head(0)

    if mostFrequent == Color.Black then Random.shuffle(Color.values.filterNot(_ == Color.Black)).toList.head
    else mostFrequent
