package controller

import model.achievements.{Achievement, AchievementData, AchievementGenerator, AchievementObservable, Event}
import utils.JsonUtils

object AchievementController:
  private val PROJECT_ROOT: String = System.getProperty("user.dir")
  private val ACHIEVEMENT_FILEPATH: String = s"$PROJECT_ROOT/data/achievement.json"

  private val achievementObservable: AchievementObservable = AchievementObservable()
  
  def initialize(): Unit =
    achievementObservable.addObservers(AchievementGenerator().achievementList)
    JsonUtils.loadFromFile[List[AchievementData]](ACHIEVEMENT_FILEPATH) = achievementData match
      case Success(achievementData) =>
        achievementObservable.loadDataFromAchievementData(achievementData)
      case Failure(_) =>
        // Load default achievements if file loading fails
        val defaultAchievements = AchievementGenerator().achievementList
        achievementObservable.addObservers(defaultAchievements)
  
  def notifyAchievements(event: Event[?]): Unit =
    achievementObservable.notifyObservers(event)

  def saveAchievements(): Unit =
    JsonUtils.saveToFile(ACHIEVEMENT_FILEPATH, achievementObservable.generateAchievementData())

  def resetAchievements(): Unit =
    achievementObservable.clearObservers()
    achievementObservable.addObservers(AchievementGenerator().achievementList)
    JsonUtils.saveToFile(ACHIEVEMENT_FILEPATH, achievementObservable.generateAchievementData())

  def achievementList: List[Achievement] = achievementObservable.getObservers
  
  
