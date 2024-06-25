import model.achievements.PropertyImpl
import org.scalatest.funsuite.AnyFunSuite
import utils.{ComparisonOperator, GameEvent}

class PropertyTest extends AnyFunSuite:

  test("Should get the property name."):
    val property = PropertyImpl("test", 1, ComparisonOperator.Equal)
    assert(property.name == "test")

  test("Should trigger when called by GameEvent."):
    val property = PropertyImpl("test", 1, ComparisonOperator.Equal)
    property.update(GameEvent("test", 0))
    assert(!property.checkProperty())
    property.update(GameEvent("not", 1))
    assert(!property.checkProperty())
    property.update(GameEvent("test", 1))
    assert(property.checkProperty())

  test("Should work with different comparison operators."):
    val propertyGreater = PropertyImpl("test", 1, ComparisonOperator.Greater)
    propertyGreater.update(GameEvent("test", 0))
    assert(!propertyGreater.checkProperty())
    propertyGreater.update(GameEvent("test", 2))
    assert(propertyGreater.checkProperty())

    val propertyLess = PropertyImpl("test", 1, ComparisonOperator.Less)
    propertyLess.update(GameEvent("test", 2))
    assert(!propertyLess.checkProperty())
    propertyLess.update(GameEvent("test", 0))
    assert(propertyLess.checkProperty())
