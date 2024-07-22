package controller

import model.settings.Difficulty.Difficulty
import model.settings.{GameSettings, Settings, SettingsImpl}
import view.settings.SettingsGui

class SettingsController:
  private val PROJECT_ROOT: String = System.getProperty("user.dir")
  private val SETTINGS_FILEPATH: String = s"$PROJECT_ROOT/config/settings.json"

  private var gui: Option[SettingsGui] = None
  val settings: Settings = SettingsImpl(SETTINGS_FILEPATH)

  def saveSettings(gameSettings: GameSettings): Unit =
    settings.updateSettings(gameSettings)

  def resetSettings(): Unit =
    settings.updateSettings(GameSettings.DEFAULT_SETTINGS)