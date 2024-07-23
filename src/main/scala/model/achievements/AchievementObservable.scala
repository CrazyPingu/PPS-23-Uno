package model.achievements

trait AchievementObservable extends Observable:

  def saveAchievements(): Unit
  
  def loadAchievements(): Unit
