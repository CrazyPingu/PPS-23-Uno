package model.settings

import play.api.libs.json.{Json, OFormat}

case class GameSettings(difficulty: String)

object GameSettings:
  implicit val gameSettingsFormat: OFormat[GameSettings] = Json.format[GameSettings]
  val defaultSettings: GameSettings = GameSettings("Easy")
