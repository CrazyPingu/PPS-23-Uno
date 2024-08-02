package model.achievements

/**
 * A trait representing an observer.
 */
trait Observer:

  /**
   * Updates the observer with an event.
   * @param event The event to update the observer with.
   */
  def update(event: Event[?]): Unit

/**
 * A trait representing an achievement that can be observed.
 */
trait Achievement extends Observer:

  val id: Int

  val description: String

  var isAchieved: Boolean

/**
 * A trait representing a boolean achievement.
 */
trait BooleanAchievement extends Achievement

/**
 * A trait representing a numeric achievement.
 */
trait NumericAchievement extends Achievement:

  val threshold: Int

  val comparator: ComparisonOperator

/**
 * Companion object for the `Achievement` trait.
 */
object Achievement:

  /**
   * Creates a boolean achievement.
   *
   * @param id The unique identifier of the achievement.
   * @param description The description of the achievement.
   * @param isAchieved Indicates whether the achievement has been achieved.
   * @return A new instance of `BooleanAchievementImpl`.
   */
  def apply(id: Int, description: String, isAchieved: Boolean): Achievement =
    BooleanAchievementImpl(id, description, isAchieved)

  /**
   * Creates a numeric achievement.
   *
   * @param id          The unique identifier of the achievement.
   * @param description The description of the achievement.
   * @param isAchieved  Indicates whether the achievement has been achieved.
   * @param threshold   The threshold value for the numeric achievement.
   * @param comparator  The comparator used to evaluate the numeric achievement.
   * @return A new instance of `NumericAchievementImpl`.
   */
  def apply(
    id: Int,
    description: String,
    isAchieved: Boolean,
    threshold: Int,
    comparator: ComparisonOperator
  ): Achievement =
    NumericAchievementImpl(id, description, isAchieved, threshold, comparator)

  /**
   * A private class representing a boolean achievement implementation.
   *
   * @param id          The unique identifier of the achievement.
   * @param description The description of the achievement.
   * @param isAchieved  Indicates whether the achievement has been achieved.
   */
  private class BooleanAchievementImpl(val id: Int, val description: String, var isAchieved: Boolean)
      extends BooleanAchievement:
    override def update(event: Event[?]): Unit = event match
      case Event(id, data: Boolean) if event.id == this.id => isAchieved = data
      case _                                               => ()

  /**
   * A private class representing a numeric achievement implementation.
   *
   * @param id          The unique identifier of the achievement.
   * @param description The description of the achievement.
   * @param isAchieved  Indicates whether the achievement has been achieved.
   * @param threshold   The threshold value for the numeric achievement.
   * @param comparator  The comparator used to evaluate the numeric achievement.
   */
  private class NumericAchievementImpl(
    val id: Int,
    val description: String,
    var isAchieved: Boolean,
    val threshold: Int,
    val comparator: ComparisonOperator
  ) extends NumericAchievement:
    override def update(event: Event[?]): Unit = event match
      case Event(id, data: Int) if event.id == this.id => isAchieved = comparator.compare(data, threshold)
      case _                                           => ()
