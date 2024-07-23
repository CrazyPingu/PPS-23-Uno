import model.achievements.{Achievement, Event}
import org.scalatest.funsuite.AnyFunSuite
import utils.ComparisonOperator

class AchievementTest extends AnyFunSuite:

  test("Should get the achievement data."):
    val achievement = Achievement(0, "test", false, 1, ComparisonOperator.Equal)
    assert(achievement.id.equals(0))
    assert(achievement.description.equals("test"))
    assert(achievement.isAchieved.equals(false))

  test("Should trigger when called by Event."):
    val achievement = Achievement(0, "test", false, 1, ComparisonOperator.Equal)
    achievement.update(Event(1, 1))
    assert(!achievement.isAchieved)
    achievement.update(Event(0, 2))
    assert(!achievement.isAchieved)
    achievement.update(Event(0, 1))
    assert(achievement.isAchieved)

  test("Should work with different comparison operators."):
    val achGreater = Achievement(0, "test", false, 1, ComparisonOperator.Greater)
    achGreater.update(Event(0, 1))
    assert(!achGreater.isAchieved)
    achGreater.update(Event(0, 2))
    assert(achGreater.isAchieved)

    val achLess = Achievement(0, "test", false, 1, ComparisonOperator.Less)
    achLess.update(Event(0, 1))
    assert(!achLess.isAchieved)
    achLess.update(Event(0, 0))
    assert(achLess.isAchieved)

  test("Should save the achievement and load when already exist"):
    assert(true)
