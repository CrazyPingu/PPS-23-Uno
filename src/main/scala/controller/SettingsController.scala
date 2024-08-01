package controller

import model.settings.Difficulty.Difficulty
import model.settings.{Settings, SettingsManager}
import view.settings.SettingsGui

object SettingsController:
  private val PROJECT_ROOT: String = System.getProperty("user.dir")
  private val SETTINGS_FILEPATH: String = s"$PROJECT_ROOT/config/settings.json"

  private val settingsManager: SettingsManager = SettingsManager(SETTINGS_FILEPATH)

  def getCurrentSettings: Settings =
    settingsManager.settings

  def saveSettings(settings: Settings): Unit =
    settingsManager.updateSettings(settings)

  def resetSettings(): Unit =
    settingsManager.updateSettings(Settings.DEFAULT_SETTINGS)
