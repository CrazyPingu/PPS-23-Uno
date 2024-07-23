package model.achievements
import play.api.libs.functional.syntax.toFunctionalBuilderOps
import play.api.libs.json.{JsPath, Reads, Writes}
import utils.{ComparisonOperator, comparisonOperatorFormat}

class Achievement(private val achID: Int, private val achDesc: String, private val achieved: Boolean, private val achThreshold: Int, private val achComparator: ComparisonOperator) extends Observer:
  val id: Int = achID
  val description: String = achDesc
  var isAchieved: Boolean = achieved
  private val threshold: Int = achThreshold
  private val comparator: ComparisonOperator = achComparator

  override def update(event: Event): Unit = event.id match
    case `id` if this.id.equals(event.id) =>
      isAchieved = comparator.compare(event.data, threshold)
    case _ => // Do nothing

object Achievement:
  implicit val achievementWrites: Writes[Achievement] = (
    (JsPath \ "id").write[Int] and
      (JsPath \ "description").write[String] and
      (JsPath \ "isAchieved").write[Boolean] and
      (JsPath \ "threshold").write[Int] and
      (JsPath \ "comparator").write[ComparisonOperator]
    )(ach => (ach.id, ach.description, ach.isAchieved, ach.threshold, ach.comparator))

  implicit val achievementReads: Reads[Achievement] = (
    (JsPath \ "id").read[Int] and
      (JsPath \ "description").read[String] and
      (JsPath \ "isAchieved").read[Boolean] and
      (JsPath \ "threshold").read[Int] and
      (JsPath \ "comparator").read[ComparisonOperator]
    )(new Achievement(_, _, _, _, _))
