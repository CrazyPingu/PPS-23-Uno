package controller

import model.achievements.{Achievement, AchievementGenerator, AchievementObservable, Event}
import utils.JsonUtils

class AchievementController:
  private val PROJECT_ROOT: String = System.getProperty("user.dir")
  private val ACHIEVEMENT_FILEPATH: String = s"$PROJECT_ROOT/achievement/achievement.json"

  private var achievementObservable: AchievementObservable = AchievementObservable()
  achievementObservable.addObservers(
    JsonUtils.loadFromFile[List[Achievement]](ACHIEVEMENT_FILEPATH).getOrElse(AchievementGenerator().achievementList)
  )

  def notifyAchievements(event: Event): Unit =
    achievementObservable.notifyObserver(event)

  def saveAchievements(): Unit =
    JsonUtils.saveToFile(ACHIEVEMENT_FILEPATH, achievementObservable.achievementList)
  
  def resetAchievements(): Unit =
    achievementObservable = AchievementObservable()
    achievementObservable.addObservers(AchievementGenerator().achievementList)
    JsonUtils.saveToFile(ACHIEVEMENT_FILEPATH, AchievementGenerator().achievementList)

  def achievementList: List[Achievement] = achievementObservable.achievementList