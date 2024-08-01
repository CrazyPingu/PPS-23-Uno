package model.achievements

import play.api.libs.json.{Format, Json}

trait IdentifiableData[T, U]:
  def id: T

  def data: U

object IdentifiableData:
  implicit val format: Format[IdentifiableData[_, _]] = Json.format

case class Event[T](id: Int, data: T) extends IdentifiableData[Int, T]

case class AchievementData(id: Int, isAchieved: Boolean) extends IdentifiableData[Int, Boolean]
