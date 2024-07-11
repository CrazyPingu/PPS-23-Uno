package model.settings

trait Settings:

  def settings: GameSettings

  def updateSettings(newSettings: GameSettings): Unit

  def resetSettings(): Unit
