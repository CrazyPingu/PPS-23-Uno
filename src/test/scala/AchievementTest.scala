import model.achievements.PropertyImpl
import org.scalatest.funsuite.AnyFunSuite
import utils.ComparisonOperator

class AchievementTest extends AnyFunSuite:
    
  test("Achieve something with 1 property"):
    val property1 = PropertyImpl("test", 1, ComparisonOperator.Equal)

    assert(true)

  test("Achieve something with multiple properties"):
    assert(true)

  test("Fail to achieve"):
    assert(true)
  
  test("Save achievement"):
    assert(true)

  test("Load achievement"):
    assert(true)
