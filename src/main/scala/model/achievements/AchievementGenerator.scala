package model.achievements

import model.achievements.{Achievement, AchievementId, ComparisonOperator}

class AchievementGenerator:
  val achievementList: List[Achievement] = List[Achievement](
    Achievement(AchievementId.firstCardAchievement.value, "Play the first card.", false, 1, ComparisonOperator.Equal),
    Achievement(AchievementId.firstPlus4Achievement.value, "Play the first +4 card.", false),
    Achievement(AchievementId.firstColorChangeAchievement.value, "Play the first color change card.", false),
    Achievement(AchievementId.firstWinAchievement.value, "Win your game for the first time.", false),
    Achievement(AchievementId.firstLoseAchievement.value, "Lose your game for the first time.", false),
    Achievement(
      AchievementId.hold2CardsAchievement.value,
      "Hold 2 cards in your hand.",
      false,
      2,
      ComparisonOperator.LessThanOrEqual
    )
  )
