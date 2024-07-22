package model.settings

import model.settings.Difficulty.{Difficulty, Easy}
import play.api.libs.json.{Format, Json, OFormat, Reads, Writes}

case class GameSettings(difficulty: Difficulty, startCardValue: Int)

object GameSettings:
  implicit val gameSettingsFormat: OFormat[GameSettings] = Json.format[GameSettings]
  val defaultSettings: GameSettings = GameSettings(Difficulty.Easy, 7)

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

