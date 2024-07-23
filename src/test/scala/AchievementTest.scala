import model.achievements.{Achievement, ComparisonOperator, Event}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.funsuite.AnyFunSuite
import utils.JsonUtils

import java.nio.file.{Files, Paths}

class AchievementTest extends AnyFunSuite with BeforeAndAfterAll:

  private val projectRoot: String = System.getProperty("user.dir")
  private val achievementFilePath: String = s"$projectRoot/achievementTest/achievement.json"

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

  test("Should save and load the achievement"):
    val achievement = Achievement(0, "test", false, 1, ComparisonOperator.Equal)
    JsonUtils.saveToFile(achievementFilePath, achievement)
    val jsonData: Option[Achievement] = JsonUtils.loadFromFile[Achievement](achievementFilePath)
    assert(jsonData.isDefined)

    val jsonAchievement: Achievement = jsonData.get
    assert(achievement.id.equals(jsonAchievement.id))
    assert(achievement.description.equals(jsonAchievement.description))
    assert(achievement.isAchieved.equals(jsonAchievement.isAchieved))

    jsonAchievement.update(Event(0, 1))
    assert(jsonAchievement.isAchieved)

  override def afterAll(): Unit =
    Files.delete(Paths.get(achievementFilePath))
    Files.delete(Paths.get(achievementFilePath).getParent)

