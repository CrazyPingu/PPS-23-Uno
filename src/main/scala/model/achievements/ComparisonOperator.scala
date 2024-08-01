package model.achievements

import model.achievements.ComparisonOperator.{Equal, GreaterThan, GreaterThanOrEqual, LessThan, LessThanOrEqual}
import play.api.libs.json.{Format, JsError, JsResult, JsString, JsSuccess, JsValue}

sealed trait ComparisonOperator:
  def compare(x: Int, y: Int): Boolean

object ComparisonOperator:
  case object LessThan extends ComparisonOperator:
    override def compare(x: Int, y: Int): Boolean = x < y

  case object LessThanOrEqual extends ComparisonOperator:
    override def compare(x: Int, y: Int): Boolean = x <= y

  case object Equal extends ComparisonOperator:
    override def compare(x: Int, y: Int): Boolean = x == y

  case object GreaterThanOrEqual extends ComparisonOperator:
    override def compare(x: Int, y: Int): Boolean = x >= y

  case object GreaterThan extends ComparisonOperator:
    override def compare(x: Int, y: Int): Boolean = x > y

implicit val comparisonOperatorFormat: Format[ComparisonOperator] = new Format[ComparisonOperator]:
  def reads(json: JsValue): JsResult[ComparisonOperator] = json match
    case JsString("LessThan")           => JsSuccess(LessThan)
    case JsString("LessThanOrEqual")    => JsSuccess(LessThanOrEqual)
    case JsString("Equal")              => JsSuccess(Equal)
    case JsString("GreaterThanOrEqual") => JsSuccess(GreaterThanOrEqual)
    case JsString("GreaterThan")        => JsSuccess(GreaterThan)
    case _                              => JsError("Unknown ComparisonOperator")

  def writes(op: ComparisonOperator): JsValue = JsString(op.toString)
