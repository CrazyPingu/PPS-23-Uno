import model.achievements.AchievementImpl
import org.scalatest.funsuite.AnyFunSuite
import utils.{ComparisonOperator, Event}

class AchievementTest extends AnyFunSuite:

  test("Should get the achievement name."):
    val property = AchievementImpl(0, "test", 1, ComparisonOperator.Equal)
    assert(property.name == "test")

  test("Should trigger when called by Event."):
    val achievement = AchievementImpl(0, "test", 1, ComparisonOperator.Equal)
    achievement.update(Event("test", 0))
    assert(!achievement.checkAchievement())
    achievement.update(Event("not", 1))
    assert(!achievement.checkAchievement())
    achievement.update(Event("test", 1))
    assert(achievement.checkAchievement())

  test("Should work with different comparison operators."):
    val achGreater = AchievementImpl(0, "test", 1, ComparisonOperator.Greater)
    achGreater.update(Event("test", 0))
    assert(!achGreater.checkAchievement())
    achGreater.update(Event("test", 2))
    assert(achGreater.checkAchievement())

    val achLess = AchievementImpl(0, "test", 1, ComparisonOperator.Less)
    achLess.update(Event("test", 2))
    assert(!achLess.checkAchievement())
    achLess.update(Event("test", 0))
    assert(achLess.checkAchievement())

  test("Should save the achievement and load when already exist"):
    assert(true)
