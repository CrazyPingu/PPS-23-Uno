package controller

import model.settings.{Settings, SettingsManager}

/**
 * The SettingsController object manages the settings of the game.
 */
object SettingsController:
  private val PROJECT_ROOT: String = System.getProperty("user.dir")
  private val SETTINGS_FILEPATH: String = s"$PROJECT_ROOT/config/settings.json"

  private val settingsManager: SettingsManager = SettingsManager(SETTINGS_FILEPATH)

  /**
   * Gets the current settings of the game.
   *
   * @return The current settings.
   */
  def getCurrentSettings: Settings =
    settingsManager.settings

  /**
   * Saves the provided settings.
   *
   * @param settings The settings to be saved.
   */
  def saveSettings(settings: Settings): Unit =
    settingsManager.updateSettings(settings)

  /**
   * Resets the settings to their default values.
   */
  def resetSettings(): Unit =
    settingsManager.updateSettings(Settings.DEFAULT_SETTINGS)
