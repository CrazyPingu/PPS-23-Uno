package model.settings

trait Settings:

  def gameSettings: GameSettings

  def updateSettings(newSettings: GameSettings): Unit

  def resetSettings(): Unit
