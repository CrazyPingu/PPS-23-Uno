package model.achievements

trait Observable:

  def addObserver(observer: Observer): Unit

  def addObservers(observers: List[Observer]): Unit

  def removeObserver(observer: Observer): Unit

  def notifyObserver(event: Event): Unit
