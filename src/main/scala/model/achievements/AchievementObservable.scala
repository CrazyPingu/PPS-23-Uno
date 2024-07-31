package model.achievements

class AchievementObservable extends Observable:
  var achievementList: List[Achievement] = List()

  override def addObserver(observer: Observer): Unit = observer match
    case observer: Achievement => achievementList = observer :: achievementList
    case _ => throw new IllegalArgumentException("Invalid observer type. It must be an Achievement.")

  override def addObservers(observers: List[Observer]): Unit =
    observers.foreach(addObserver)

  override def removeObserver(observer: Observer): Unit =
    achievementList = achievementList.filterNot(_ == observer)

  override def notifyObserver(event: Event): Unit =
    achievementList.foreach(_.update(event))
