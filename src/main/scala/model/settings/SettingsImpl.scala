package model.settings

class SettingsImpl extends Settings:
  
  SettingsImpl()

  def loadSettings(): Option[GameSettings] = ???

  def saveSettings(settings: GameSettings): Unit = ???

  def getSettings: GameSettings = ???

  def updateSettings(newSettings: GameSettings): Unit = ???

