package controller

import model.achievements.{Achievement, AchievementData, AchievementGenerator, AchievementObservable, Event}
import utils.JsonUtils

object AchievementController:
  private val PROJECT_ROOT: String = System.getProperty("user.dir")
  private val ACHIEVEMENT_FILEPATH: String = s"$PROJECT_ROOT/data/achievement.json"

  private val achievementObservable: AchievementObservable = AchievementObservable()

  initialize()

  private def initialize(): Unit =
    achievementObservable.addObservers(AchievementGenerator().achievementList)
    val achievementData: Option[List[AchievementData]] =
      JsonUtils.loadFromFile[List[AchievementData]](ACHIEVEMENT_FILEPATH)
    if achievementData.isDefined then achievementObservable.loadDataFromAchievementData(achievementData.get)

  def notifyAchievements(event: Event[?]): Unit =
    achievementObservable.notifyObservers(event)

  def saveAchievements(): Unit =
    JsonUtils.saveToFile(ACHIEVEMENT_FILEPATH, achievementObservable.generateAchievementData())

  def resetAchievements(): Unit =
    achievementObservable.clearObservers()
    achievementObservable.addObservers(AchievementGenerator().achievementList)
    JsonUtils.saveToFile(ACHIEVEMENT_FILEPATH, achievementObservable.generateAchievementData())

  def achievementList: List[Achievement] = achievementObservable.getObservers
