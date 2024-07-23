package model.achievements

trait Observer:
  def update(event: Event): Unit

trait Observable:

  def addObserver(observer: Observer): Unit

  def addObservers(observers: List[Observer]): Unit

  def removeObserver(observer: Observer) : Unit
    
  def notifyObserver(event: Event) : Unit
    
    