package model.achievements

import play.api.libs.functional.syntax.toFunctionalBuilderOps
import play.api.libs.json.{Format, JsError, JsPath, JsResult, JsSuccess, JsValue, Json, Reads, Writes}

trait Observer:
  def update(event: Event[?]): Unit

trait Achievement extends Observer:
  def id: Int

  def description: String

  var isAchieved: Boolean

trait BooleanAchievement extends Achievement

trait NumericAchievement extends Achievement:
  def threshold: Int

  def comparator: ComparisonOperator

object Achievement:
  def apply(id: Int, description: String, isAchieved: Boolean): Achievement =
    BooleanAchievementImpl(id, description, isAchieved)

  def apply(
    id: Int,
    description: String,
    isAchieved: Boolean,
    threshold: Int,
    comparator: ComparisonOperator
  ): Achievement =
    NumericAchievementImpl(id, description, isAchieved, threshold, comparator)

  private class BooleanAchievementImpl(val id: Int, val description: String, var isAchieved: Boolean)
      extends BooleanAchievement:
    override def update(event: Event[?]): Unit = event match
      case Event(id, data: Boolean) if event.id == id => isAchieved = data
      case _                                          => ()

  private class NumericAchievementImpl(
    val id: Int,
    val description: String,
    var isAchieved: Boolean,
    val threshold: Int,
    val comparator: ComparisonOperator
  ) extends NumericAchievement:
    override def update(event: Event[?]): Unit = event match
      case Event(id, data: Int) if event.id == id => isAchieved = comparator.compare(data, threshold)
      case _                                      => ()
