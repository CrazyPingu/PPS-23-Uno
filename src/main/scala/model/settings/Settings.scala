package model.settings

trait Settings:

  def getSettings: GameSettings

  def updateSettings(newSettings: GameSettings): Unit

  def resetSettings(): Unit
