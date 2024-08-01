package model.settings

import model.settings.Difficulty.Difficulty
import play.api.libs.json.{Format, Json, OFormat}
import sun.security.util.FilePaths
import utils.JsonUtils

case class Settings(difficulty: Difficulty, startCardValue: Int, handicap: Int)

object Settings:
  val DEFAULT_SETTINGS: Settings = Settings(Difficulty.Easy, 7, 0)
  implicit val gameSettingsFormat: OFormat[Settings] = Json.format[Settings]

trait SettingsManager:

  var settings: Settings = Settings.DEFAULT_SETTINGS

  def updateSettings(newSettings: Settings): Unit

  def resetSettings(): Unit

object SettingsManager:
  def apply(filePath: String): SettingsManager = SettingsManagerImpl(filePath)

  private class SettingsManagerImpl(private val filePath: String) extends SettingsManager:
    settings = JsonUtils.loadFromFile[Settings](filePath).getOrElse(Settings.DEFAULT_SETTINGS)

    override def updateSettings(newSettings: Settings): Unit =
      settings = newSettings
      JsonUtils.saveToFile(filePath, settings)

    override def resetSettings(): Unit =
      settings = Settings.DEFAULT_SETTINGS
      JsonUtils.saveToFile(filePath, settings)

object Difficulty extends Enumeration:
  type Difficulty = Value
  val Easy, Hard = Value
  implicit val difficultyFormat: Format[Difficulty] = Json.formatEnum(Difficulty)

  def fromInt(value: Int): Difficulty = value match
    case 0 => Easy
    case 1 => Hard
    case _ => throw new IllegalArgumentException("Invalid difficulty value")

  def toInt(difficulty: Difficulty): Int = difficulty match
    case Easy => 0
    case Hard => 1
