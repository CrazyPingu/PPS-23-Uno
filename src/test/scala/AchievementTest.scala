import controller.AchievementController.{ACHIEVEMENT_FILEPATH, achievementObservable}
import model.achievements.{Achievement, AchievementData, AchievementGenerator, AchievementId, AchievementObservable, BooleanAchievement, ComparisonOperator, Event, NumericAchievement}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.funsuite.AnyFunSuite
import utils.JsonUtils

import java.nio.file.{Files, Paths}

class AchievementTest extends AnyFunSuite with BeforeAndAfterAll:

  private val projectRoot: String = System.getProperty("user.dir")
  private val achievementTestPath: String = s"$projectRoot/test/achievement.json"

  test("Correct creation of achievement."):
    val booleanAchievement = Achievement(1, "test1", false)
    assert(booleanAchievement.isInstanceOf[BooleanAchievement])
    assert(booleanAchievement.id.equals(1))
    assert(booleanAchievement.description.equals("test1"))
    assert(booleanAchievement.isAchieved.equals(false))

    val numericAchievement = Achievement(2, "test2", false, 1, ComparisonOperator.Equal)
    assert(numericAchievement.isInstanceOf[NumericAchievement])
    assert(numericAchievement.id.equals(2))
    assert(numericAchievement.description.equals("test2"))
    assert(numericAchievement.isAchieved.equals(false))

  test("Should trigger when called by Event."):
    val achievement = Achievement(0, "test", false)
    achievement.update(Event(1, false))
    assert(!achievement.isAchieved)
    achievement.update(Event(0, false))
    assert(!achievement.isAchieved)
    achievement.update(Event(0, true))
    assert(achievement.isAchieved)

  test("Numeric achievements should work with different comparison operators."):
    val achGreater = Achievement(1, "test1", false, 1, ComparisonOperator.GreaterThan)
    achGreater.update(Event(1, 1))
    assert(!achGreater.isAchieved)
    achGreater.update(Event(1, 2))
    assert(achGreater.isAchieved)

    val achLess = Achievement(2, "test2", false, 1, ComparisonOperator.LessThan)
    achLess.update(Event(2, 1))
    assert(!achLess.isAchieved)
    achLess.update(Event(2, 0))
    assert(achLess.isAchieved)

  test("Should save and load the achievements data"):
    val achievementObservable: AchievementObservable = AchievementObservable()
    achievementObservable.addObservers(AchievementGenerator().achievementList)
    achievementObservable.notifyObservers(Event(AchievementId.firstCardAchievement.value, true))
    JsonUtils.saveToFile(achievementTestPath, achievementObservable.generateAchievementData())

    val jsonData: Option[List[AchievementData]] = JsonUtils.loadFromFile[List[AchievementData]](achievementTestPath)
    assert(jsonData.isDefined)

    val jsonAchievementData: List[AchievementData] = jsonData.get
    assert(achievementObservable.generateAchievementData() == jsonAchievementData)
  
  override def afterAll(): Unit =
    Files.delete(Paths.get(achievementTestPath))
    Files.delete(Paths.get(achievementTestPath).getParent)
