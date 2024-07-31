package model.achievements

trait Observer:
  def update(event: Event): Unit
