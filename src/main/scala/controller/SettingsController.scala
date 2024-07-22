package controller

import model.settings.{GameSettings, Settings, SettingsImpl}
import view.settings.SettingsGui

class SettingsController:
  private val PROJECT_ROOT: String = System.getProperty("user.dir")
  private val SETTINGS_FILEPATH: String = s"$PROJECT_ROOT/config/settings.json"

  private var gui: Option[SettingsGui] = None
  private val settings: Settings = SettingsImpl(SETTINGS_FILEPATH)

  