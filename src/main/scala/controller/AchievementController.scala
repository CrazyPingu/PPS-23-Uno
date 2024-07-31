package controller

import model.achievements.{Achievement, AchievementGenerator, AchievementObservable, Event}
import utils.JsonUtils

object AchievementController:
  private val PROJECT_ROOT: String = System.getProperty("user.dir")
  private val ACHIEVEMENT_FILEPATH: String = s"$PROJECT_ROOT/achievement/achievement.json"

  AchievementObservable.addObservers(
    JsonUtils.loadFromFile[List[Achievement]](ACHIEVEMENT_FILEPATH).getOrElse(AchievementGenerator().achievementList)
  )

  def notifyAchievements(event: Event): Unit =
    AchievementObservable.notifyObservers(event)

  def saveAchievements(): Unit =
    JsonUtils.saveToFile(ACHIEVEMENT_FILEPATH, AchievementObservable.getObservers)

  def resetAchievements(): Unit =
    AchievementObservable.clearObservers()
    AchievementObservable.addObservers(AchievementGenerator().achievementList)
    JsonUtils.saveToFile(ACHIEVEMENT_FILEPATH, AchievementGenerator().achievementList)

  def achievementList: List[Achievement] = AchievementObservable.getObservers
