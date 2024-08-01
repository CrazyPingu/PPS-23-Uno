package model.achievements

object AchievementId extends Enumeration:
  type AchievementId = Value

  val FirstCardAchievement: Value = Value(0, "FirstCardAchievement")
  val FirstPlus4Achievement: Value = Value(1, "FirstPlus4Achievement")
  val FirstColorChangeAchievement: Value = Value(2, "FirstColorChangeAchievement")
  val FirstWinAchievement: Value = Value(3, "FirstWinAchievement")
  val FirstLoseAchievement: Value = Value(4, "FirstLoseAchievement")
  val Hold2CardsAchievement: Value = Value(5, "Hold2CardsAchievement")
