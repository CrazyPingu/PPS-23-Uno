package model.settings

import model.settings.Difficulty.Difficulty
import play.api.libs.json.{Format, Json, OFormat}
import utils.JsonUtils

/**
 * A case class representing the settings of the game.
 *
 * @param difficulty     The difficulty level of the game.
 * @param startCardValue The starting card value.
 * @param handicap       The handicap value.
 */
case class Settings(difficulty: Difficulty, startCardValue: Int, handicap: Int)

/**
 * Companion object for the `Settings` case class.
 */
object Settings:
  val DEFAULT_SETTINGS: Settings = Settings(Difficulty.Easy, 7, 0)

  /**
   * Implicit format for serializing and deserializing `Settings` instances.
   */
  implicit val gameSettingsFormat: OFormat[Settings] = Json.format[Settings]

/**
 * A trait representing a settings manager.
 */
trait SettingsManager:

  var settings: Settings = Settings.DEFAULT_SETTINGS

  /**
   * Updates the current settings with new settings.
   *
   * @param newSettings The new settings to be applied.
   */
  def updateSettings(newSettings: Settings): Unit

/**
 * Companion object for the `SettingsManager` trait.
 */
object SettingsManager:

  /**
   * Creates a new instance of `SettingsManager`.
   *
   * @param filePath The file path where the settings are stored.
   * @return A new instance of `SettingsManagerImpl`.
   */
  def apply(filePath: String): SettingsManager = SettingsManagerImpl(filePath)

  /**
   * A private class representing the implementation of `SettingsManager`.
   *
   * @param filePath The file path where the settings are stored.
   */
  private class SettingsManagerImpl(private val filePath: String) extends SettingsManager:
    settings = JsonUtils.loadFromFile[Settings](filePath).getOrElse(Settings.DEFAULT_SETTINGS)

    override def updateSettings(newSettings: Settings): Unit =
      settings = newSettings
      JsonUtils.saveToFile(filePath, settings)

/**
 * An enumeration representing the difficulty levels of the game.
 */
object Difficulty extends Enumeration:
  type Difficulty = Value
  val Easy, Hard = Value
  implicit val difficultyFormat: Format[Difficulty] = Json.formatEnum(Difficulty)

  /**
   * Converts an integer to a `Difficulty` value.
   *
   * @param value The integer value to be converted.
   * @return The corresponding `Difficulty` value.
   * @throws IllegalArgumentException if the integer value is invalid.
   */
  def fromInt(value: Int): Difficulty = value match
    case 0 => Easy
    case 1 => Hard
    case _ => throw new IllegalArgumentException("Invalid difficulty value")

  /**
   * Converts a `Difficulty` value to an integer.
   *
   * @param difficulty The `Difficulty` value to be converted.
   * @return The corresponding integer value.
   */
  def toInt(difficulty: Difficulty): Int = difficulty match
    case Easy => 0
    case Hard => 1
