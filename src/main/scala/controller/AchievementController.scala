package controller

import model.achievements.{Achievement, AchievementData, AchievementGenerator, AchievementObservable, Event}
import utils.JsonUtils

/**
 * The AchievementController object manages the achievements in the game.
 */
object AchievementController:
  private val PROJECT_ROOT: String = System.getProperty("user.dir")
  private val ACHIEVEMENT_FILEPATH: String = s"$PROJECT_ROOT/data/achievement.json"

  private val achievementObservable: AchievementObservable = AchievementObservable()
  initialize()

  /**
   * Initializes the achievement controller by loading achievements from the file.
   */
  private def initialize(): Unit =
    achievementObservable.addObservers(AchievementGenerator().achievementList)
    val achievementData: Option[List[AchievementData]] =
      JsonUtils.loadFromFile[List[AchievementData]](ACHIEVEMENT_FILEPATH)
    if achievementData.isDefined then achievementObservable.loadDataFromAchievementData(achievementData.get)

  /**
   * Notifies the achievement observers of an event.
   *
   * @param event The event to notify the observers about.
   */
  def notifyAchievements(event: Event[?]): Unit =
    achievementObservable.notifyObservers(event)

  /**
   * Saves the current achievements to a file.
   */
  def saveAchievements(): Unit =
    JsonUtils.saveToFile(ACHIEVEMENT_FILEPATH, achievementObservable.generateAchievementData())

  /**
   * Resets the achievements to their initial state and saves them to a file.
   */
  def resetAchievements(): Unit =
    achievementObservable.clearObservers()
    achievementObservable.addObservers(AchievementGenerator().achievementList)
    JsonUtils.saveToFile(ACHIEVEMENT_FILEPATH, achievementObservable.generateAchievementData())

  /**
   * Gets the list of current achievements.
   *
   * @return The list of current achievements.
   */
  def achievementList: List[Achievement] = achievementObservable.getObservers
