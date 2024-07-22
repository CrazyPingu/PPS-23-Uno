package model.achievements

import utils.Observer

trait Achievement extends Observer:

  def checkAchievement(): Boolean