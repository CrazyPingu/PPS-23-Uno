package model.achievements

trait Observable[A <: Observer]:
  private var observers: List[A] = List()

  def addObserver(observer: A): Unit =
    observers = observer :: observers

  def addObservers(newObservers: List[A]): Unit =
    observers = newObservers ++ observers

  def notifyObservers(event: Event[?]): Unit =
    observers.foreach(_.update(event))

  def removeObserver(observer: A): Unit =
    observers = observers.filterNot(_ == observer)

  def clearObservers(): Unit =
    observers = List()

  def getObservers: List[A] = observers

trait AchievementObservable extends Observable[Achievement]:
  def generateAchievementData(): List[AchievementData] =
    getObservers.map(
      achievement => AchievementData(achievement.id, achievement.isAchieved)
    )

  def loadDataFromAchievementData(achievementData: List[AchievementData]): Unit =
    getObservers.foreach(
      achievement =>
        val data = achievementData.find(_.id == achievement.id)
        if data.isDefined then achievement.isAchieved = data.get.data
    )

object AchievementObservable:
  def apply(): AchievementObservable = new AchievementObservableImpl
  private class AchievementObservableImpl extends AchievementObservable
