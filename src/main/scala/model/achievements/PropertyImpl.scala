package model.achievements
import utils.{ComparisonOperator, GameEvent}

class PropertyImpl(private val propertyName: String, private val propThreshold: Int, private val propComparator: ComparisonOperator) extends Property:
  val name: String = propertyName
  private val threshold: Int = propThreshold
  private val comparator: ComparisonOperator = propComparator
  private var isTrue: Boolean = false

  override def update(event: GameEvent): Unit = event.propertyName match
    case name if name.equals(event.propertyName) => isTrue = comparator.compare(event.data, threshold)
    case _ => // Do nothing

  override def checkProperty(): Boolean = isTrue