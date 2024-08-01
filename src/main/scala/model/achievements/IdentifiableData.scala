package model.achievements

import play.api.libs.json.{Format, JsError, JsResult, JsSuccess, JsValue, Json, OFormat, Reads, Writes}

trait IdentifiableData[D]:
  def id: Int

  def data: D

case class Event[D](override val id: Int, override val data: D) extends IdentifiableData[D]

case class AchievementData(override val id: Int, override val data: Boolean) extends IdentifiableData[Boolean]

object AchievementData:
  given Format[AchievementData] = Json.format[AchievementData]
