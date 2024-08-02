package model.achievements

import model.achievements.ComparisonOperator.{Equal, GreaterThan, GreaterThanOrEqual, LessThan, LessThanOrEqual}
import play.api.libs.json.{Format, JsError, JsResult, JsString, JsSuccess, JsValue}

/**
 * A sealed trait representing a comparison operator.
 */
sealed trait ComparisonOperator:
  def compare: (Int, Int) => Boolean

/**
 * Companion object for the `ComparisonOperator` trait.
 * Each case object represents a comparison operator.
 */
object ComparisonOperator:

  case object LessThan extends ComparisonOperator:
    val compare: (Int, Int) => Boolean = _ < _

  case object LessThanOrEqual extends ComparisonOperator:
    val compare: (Int, Int) => Boolean = _ <= _

  case object Equal extends ComparisonOperator:
    val compare: (Int, Int) => Boolean = _ == _

  case object GreaterThanOrEqual extends ComparisonOperator:
    val compare: (Int, Int) => Boolean = _ >= _

  case object GreaterThan extends ComparisonOperator:
    val compare: (Int, Int) => Boolean = _ > _

/**
 * Implicit format for serializing and deserializing `ComparisonOperator` instances.
 */
implicit val comparisonOperatorFormat: Format[ComparisonOperator] = new Format[ComparisonOperator]:
  def reads(json: JsValue): JsResult[ComparisonOperator] = json match
    case JsString("LessThan")           => JsSuccess(LessThan)
    case JsString("LessThanOrEqual")    => JsSuccess(LessThanOrEqual)
    case JsString("Equal")              => JsSuccess(Equal)
    case JsString("GreaterThanOrEqual") => JsSuccess(GreaterThanOrEqual)
    case JsString("GreaterThan")        => JsSuccess(GreaterThan)
    case _                              => JsError("Unknown ComparisonOperator")

  def writes(op: ComparisonOperator): JsValue = JsString(op.toString)
