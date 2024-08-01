package model.achievements

import model.achievements.{Achievement, AchievementId, ComparisonOperator}

class AchievementGenerator:
  val achievementList: List[Achievement] = List[Achievement](
    Achievement(AchievementId.FirstCardAchievement.id, "Play the first card.", false),
    Achievement(AchievementId.FirstPlus4Achievement.id, "Play the first +4 card.", false),
    Achievement(AchievementId.FirstColorChangeAchievement.id, "Play the first color change card.", false),
    Achievement(AchievementId.FirstWinAchievement.id, "Win your game for the first time.", false),
    Achievement(AchievementId.FirstLoseAchievement.id, "Lose your game for the first time.", false),
    Achievement(
      AchievementId.Hold2CardsAchievement.id,
      "Hold 2 cards in your hand.",
      false,
      2,
      ComparisonOperator.LessThanOrEqual
    )
  )
