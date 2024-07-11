package model.settings

import utils.JsonUtils

class SettingsImpl(filePath: String) extends Settings:

  private val path = filePath
  private val defaultSettings = GameSettings("easy")
  private var settings: GameSettings = JsonUtils.loadFromFile[GameSettings](filePath).getOrElse(defaultSettings)

  override def getSettings: GameSettings = settings

  override def updateSettings(newSettings: GameSettings): Unit =
    settings = newSettings
    JsonUtils.saveToFile(path, settings)

  override def resetSettings(): Unit =
    settings = defaultSettings
    JsonUtils.saveToFile(path, settings)