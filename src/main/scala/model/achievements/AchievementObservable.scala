package model.achievements

/**
 * A trait representing an observable entity.
 *
 * @tparam A The type of observer that can observe this entity.
 */
trait Observable[A <: Observer]:
  private var observers: List[A] = List()

  /**
   * Adds an observer to the list of observers.
   *
   * @param observer The observer to be added.
   */
  def addObserver(observer: A): Unit =
    observers = observer :: observers

  /**
   * Adds multiple observers to the list of observers.
   *
   * @param newObservers The list of observers to be added.
   */
  def addObservers(newObservers: List[A]): Unit =
    observers = newObservers ++ observers

  /**
   * Notifies all observers of an event.
   *
   * @param event The event to notify the observers about.
   */
  def notifyObservers(event: Event[?]): Unit =
    observers.foreach(_.update(event))

  /**
   * Removes an observer from the list of observers.
   *
   * @param observer The observer to be removed.
   */
  def removeObserver(observer: A): Unit =
    observers = observers.filterNot(_ == observer)

  /**
   * Clears all observers from the list.
   */
  def clearObservers(): Unit =
    observers = List()

  /**
   * Gets the list of observers.
   *
   * @return The list of observers.
   */
  def getObservers: List[A] = observers

/**
 * A trait representing an observable achievement entity.
 */
trait AchievementObservable extends Observable[Achievement]:

  /**
   * Generates a list of achievement data from the observers.
   *
   * @return A list of `AchievementData` containing the ID and achievement status.
   */
  def generateAchievementData(): List[AchievementData] =
    getObservers.map(
      achievement => AchievementData(achievement.id, achievement.isAchieved)
    )

  /**
   * Loads achievement data into the observers.
   *
   * @param achievementData A list of `AchievementData` to be loaded.
   */
  def loadDataFromAchievementData(achievementData: List[AchievementData]): Unit =
    getObservers.foreach(
      achievement =>
        val data = achievementData.find(_.id == achievement.id)
        if data.isDefined then achievement.isAchieved = data.get.data
    )

/**
 * Companion object for the `AchievementObservable` trait.
 */
object AchievementObservable:

  /**
   * Creates a new instance of `AchievementObservable`.
   *
   * @return A new instance of `AchievementObservableImpl`.
   */
  def apply(): AchievementObservable = new AchievementObservableImpl

  /**
   * A private class representing the implementation of `AchievementObservable`.
   */
  private class AchievementObservableImpl extends AchievementObservable
