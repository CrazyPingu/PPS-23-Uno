package model.achievements

enum AchievementId(val value: Int):
  case firstCardAchievement extends AchievementId(0)
  case firstPlus4Achievement extends AchievementId(1)
  case firstColorChangeAchievement extends AchievementId(2)
  case firstWinAchievement extends AchievementId(3)
  case firstLoseAchievement extends AchievementId(4)
  case hold2CardsAchievement extends AchievementId(5)
