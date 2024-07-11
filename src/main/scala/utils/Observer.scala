package utils

trait Observer:
  def update(event: Event): Unit

trait Observable:
  private var observers: List[Observer] = List()
  
  def addObserver(observer: Any) : Unit = observer match
    case observer: Observer =>
      observers = observer :: observers
    case observer: List[_] if observer.forall(_.isInstanceOf[Observer]) =>
      observers = observer.asInstanceOf[List[Observer]] ::: observers
    case _ =>
      throw new IllegalArgumentException("Observer must be either an Observer or a List[Observer]")

  def removeObserver(observer: Observer) : Unit =
    observers = observers.filterNot(_ == observer)
    
  def notifyObserver(event: Event) : Unit =
    observers.foreach(_.update(event))
    
    