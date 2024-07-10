package utils

trait Observer:
  def update(event: Event): Unit

trait Observable:
  private var observers: List[Observer] = List()
  
  def addObserver(observer: Observer) : Unit =
    observers = observer :: observers
    
  def removeObserver(observer: Observer) : Unit =
    observers = observers.filterNot(_ == observer)
    
  def notifyObserver(event: Event) : Unit =
    observers.foreach(_.update(event))
    
    