package model.achievements
import utils.{ComparisonOperator, Event, JsonUtils}

class AchievementImpl(private val achID: Int, private val achName: String, private val achThreshold: Int, private val achComparator: ComparisonOperator) extends Achievement:
  val id: Int = achID
  val name: String = achName
  private var lastData: Option[Int] = None
  private val threshold: Int = achThreshold
  private val comparator: ComparisonOperator = achComparator

  override def update(event: Event): Unit = event.name match
    case `name` if name.equals(event.name) =>
      lastData = Option(event.data)
    case _ => // Do nothing

  override def checkAchievement(): Boolean =
    if lastData.isDefined then
      comparator.compare(lastData.get, threshold)
    else
      false