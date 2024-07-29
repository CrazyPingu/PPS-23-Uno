package model.settings

import utils.JsonUtils

class SettingsImpl(filePath: String) extends Settings:

  var gameSettings: GameSettings = JsonUtils.loadFromFile[GameSettings](filePath).getOrElse(GameSettings.DEFAULT_SETTINGS)
  
  override def updateSettings(newSettings: GameSettings): Unit =
    gameSettings = newSettings
    JsonUtils.saveToFile(filePath, gameSettings)

  override def resetSettings(): Unit =
    gameSettings = GameSettings.DEFAULT_SETTINGS
    JsonUtils.saveToFile(filePath, gameSettings)