package controller

import model.achievements.{Achievement, AchievementObservable, Event}
import utils.JsonUtils

import java.nio.file.{Files, Paths}

class AchievementController:
  private val PROJECT_ROOT: String = System.getProperty("user.dir")
  private val ACHIEVEMENT_FILEPATH: String = s"$PROJECT_ROOT/achievement/achievement.json"

  val achievementList: List[Achievement] = JsonUtils.loadFromFile[List[Achievement]](ACHIEVEMENT_FILEPATH).getOrElse(AchievementGenerator().achievementList)
  private val achievementObservable: AchievementObservable = AchievementObservable()
  achievementObservable.addObservers(achievementList)

  def notifyAchievements(event: Event): Unit =
    achievementObservable.notifyObserver(event)

  def saveAchievements(): Unit =
    JsonUtils.saveToFile(ACHIEVEMENT_FILEPATH, achievementList)
