package model.achievements

class AchievementImpl(private val achievementName: String, private val achievementDescription: String, private val propertiesList: List[Property], private val status: Boolean) extends Achievement:
  val name: String = achievementName
  val description: String = achievementDescription
  private val propList: List[Property] = propertiesList
  private var isAchieved: Boolean = status

  override def achieve(): Boolean =
    isAchieved = propList.forall(_.checkProperty())
    if isAchieved then saveAchievement()
    isAchieved

  override def saveAchievement(): Unit = ???

  override def createJsonAchievement(): Unit = ???
