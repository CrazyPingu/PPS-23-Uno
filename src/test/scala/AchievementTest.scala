import model.achievements.{AchievementImpl, PropertyImpl}
import org.scalatest.funsuite.AnyFunSuite
import utils.ComparisonOperator

class AchievementTest extends AnyFunSuite:
    
  test("Achieve something with 1 property"):
    val propertyList = List(PropertyImpl("test", 1, ComparisonOperator.Equal))
    val achievement = AchievementImpl("achievement-test", "achievement test desc", propertyList, false)
    assert(achievement.achieve())

  test("Achieve something with multiple properties"):
    assert(true)

  test("Fail to achieve"):
    assert(true)
  
  test("Save achievement"):
    assert(true)

  test("Load achievement"):
    assert(true)
