package model.achievements

trait Achievement:
    
    def achieve(): Boolean
    
    def saveAchievement(): Unit
    
    def createJsonAchievement(): Unit
    