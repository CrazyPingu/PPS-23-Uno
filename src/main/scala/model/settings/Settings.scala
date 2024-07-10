package model.settings

trait Settings:

  def loadSettings(): Option[GameSettings]

  def saveSettings(settings: GameSettings): Unit

  def getSettings: GameSettings

  def updateSettings(newSettings: GameSettings): Unit
