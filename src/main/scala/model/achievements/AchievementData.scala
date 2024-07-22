package model.achievements

import play.api.libs.json.{Json, OFormat}

case class AchievementData(ID: Int, description: String, isAchieved: Boolean)

object GameSettings:
  implicit val gameSettingsFormat: OFormat[AchievementData] = Json.format[AchievementData]