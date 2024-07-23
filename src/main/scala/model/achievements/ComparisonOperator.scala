package utils

import play.api.libs.json.{Format, JsError, JsResult, JsString, JsSuccess, JsValue}

enum ComparisonOperator:
  case Greater
  case Less
  case Equal
  
  def compare(x: Int, y: Int): Boolean = this match
    case Greater => x > y
    case Less => x < y
    case Equal => x == y

implicit val comparisonOperatorFormat: Format[ComparisonOperator] = new Format[ComparisonOperator]:
  override def reads(json: JsValue): JsResult[ComparisonOperator] = json.as[String] match
    case "Greater" => JsSuccess(ComparisonOperator.Greater)
    case "Less" => JsSuccess(ComparisonOperator.Less)
    case "Equal" => JsSuccess(ComparisonOperator.Equal)
    case _ => JsError("Unknown comparison operator")

  override def writes(comparator: ComparisonOperator): JsValue = JsString(comparator.toString)

