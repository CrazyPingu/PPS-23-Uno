package model.settings

import utils.JsonUtils

class SettingsImpl(filePath: String) extends Settings:

  private val path = filePath
  var gameSettings: GameSettings = JsonUtils.loadFromFile[GameSettings](filePath).getOrElse(GameSettings.DEFAULT_SETTINGS)
  
  override def updateSettings(newSettings: GameSettings): Unit =
    gameSettings = newSettings
    JsonUtils.saveToFile(path, gameSettings)

  override def resetSettings(): Unit =
    gameSettings = GameSettings.DEFAULT_SETTINGS
    JsonUtils.saveToFile(path, gameSettings)