package model.achievements

import play.api.libs.json.{Format, JsError, JsResult, JsSuccess, JsValue, Json, OFormat, Reads, Writes}

/**
 * A trait representing identifiable data.
 *
 * @tparam D The type of data associated with the identifier.
 */
trait IdentifiableData[D]:

  def id: Int

  def data: D

/**
 * A case class representing an event with identifiable data.
 *
 * @param id   The unique identifier of the event.
 * @param data The data associated with the event.
 * @tparam D The type of data associated with the event.
 */
case class Event[D](override val id: Int, override val data: D) extends IdentifiableData[D]

/**
 * A case class representing achievement data.
 *
 * @param id   The unique identifier of the achievement.
 * @param data The achievement status.
 */
case class AchievementData(override val id: Int, override val data: Boolean) extends IdentifiableData[Boolean]

/**
 * Companion object for the `AchievementData` case class.
 */
object AchievementData:

  /**
   * Implicit format for serializing and deserializing `AchievementData` instances.
   */
  implicit val format: Format[AchievementData] = Json.format[AchievementData]
