package model.achievements

import play.api.libs.functional.syntax.toFunctionalBuilderOps
import play.api.libs.json.{JsPath, Reads, Writes}

trait Observer:
  def update(event: Event): Unit

trait Achievement extends Observer:
  def id: Int

  def description: String

  def isAchieved: Boolean

  def threshold: Int

  def comparator: ComparisonOperator

object Achievement:

  def apply(
    id: Int,
    description: String,
    isAchieved: Boolean,
    threshold: Int,
    comparator: ComparisonOperator
  ): Achievement =
    AchievementImpl(id, description, isAchieved, threshold, comparator)

  private case class AchievementImpl(
    private val achID: Int,
    private val achDesc: String,
    private var achieved: Boolean,
    private val achThreshold: Int,
    private val achComparator: ComparisonOperator
  ) extends Achievement:
    override def id: Int = achID

    override def description: String = achDesc

    override def isAchieved: Boolean = achieved

    override def threshold: Int = achThreshold

    override def comparator: ComparisonOperator = achComparator

    override def update(event: Event): Unit =
      if event.id == id then achieved = comparator.compare(event.data, threshold)

  implicit val achievementWrites: Writes[Achievement] = (
    (JsPath \ "id").write[Int] and
      (JsPath \ "description").write[String] and
      (JsPath \ "isAchieved").write[Boolean] and
      (JsPath \ "threshold").write[Int] and
      (JsPath \ "comparator").write[ComparisonOperator]
  )(
    ach => (ach.id, ach.description, ach.isAchieved, ach.threshold, ach.comparator)
  )

  implicit val achievementReads: Reads[Achievement] = (
    (JsPath \ "id").read[Int] and
      (JsPath \ "description").read[String] and
      (JsPath \ "isAchieved").read[Boolean] and
      (JsPath \ "threshold").read[Int] and
      (JsPath \ "comparator").read[ComparisonOperator]
  )(Achievement(_, _, _, _, _))
