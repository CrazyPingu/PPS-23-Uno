package view.achievements

import controller.{AchievementController, PageController}
import utils.ImageHandler.backgroundAchievements
import view.settings.Table
import view.{Button, GridBagConstraints, Label}

import java.awt.*
import javax.swing.JPanel

/**
 * The AchievementGui class represents the GUI for displaying achievements.
 *
 * @param pageController The controller for managing page transitions.
 */
class AchievementGui private (pageController: PageController) extends JPanel:
  setLayout(new GridBagLayout())
  AchievementGui.title.setForeground(Color.BLACK)

  add(AchievementGui.title, new GridBagConstraints(0, 0, 2, AchievementGui.defaultInsets))
  add(AchievementGui.achievementTable, new GridBagConstraints(0, 1, 2, AchievementGui.defaultInsets))
  add(AchievementGui.resetAchievementsButton, new GridBagConstraints(0, 2, 1, AchievementGui.defaultInsets))
  add(AchievementGui.goBackButton, new GridBagConstraints(1, 2, 1, AchievementGui.defaultInsets))

  AchievementGui.resetAchievementsButton.addActionListener(
    _ =>
      AchievementController.resetAchievements()
      pageController.showMainMenu()
  )

  AchievementGui.goBackButton.addActionListener(
    _ => pageController.showMainMenu()
  )

  /**
   * Updates the GUI with the latest achievement data.
   */
  def updateGui(): Unit =
    remove(AchievementGui.achievementTable)
    AchievementGui.achievementTable =
      new Table(AchievementGui.getDataOnArray, AchievementGui.columnNames, Array(400, 200))
    add(AchievementGui.achievementTable, new GridBagConstraints(0, 1, 2, AchievementGui.defaultInsets))

  /**
   * Paints the background image for the achievements panel.
   *
   * @param g The graphics context.
   */
  override protected def paintComponent(g: Graphics): Unit =
    super.paintComponent(g)
    val g2d: Graphics2D = g.asInstanceOf[Graphics2D]
    g2d.drawImage(backgroundAchievements, 0, 0, getWidth, getHeight, this)

/**
 * Companion object for the AchievementGui class.
 */
object AchievementGui:
  private val defaultInsets: Insets = new Insets(10, 10, 10, 10)
  private val title: Label = new Label("Achievements:")
  private val columnNames: Array[AnyRef] = Array("Description", "Is achieved")
  private val resetAchievementsButton: Button = new Button("Reset achievements")
  private val goBackButton: Button = new Button("Go back to menu")
  private var achievementTable: Table = new Table(getDataOnArray, columnNames, Array(400, 200))

  /**
   * Creates a new AchievementGui instance.
   *
   * @param pageController The controller for managing page transitions.
   * @return A new AchievementGui instance.
   */
  def apply(pageController: PageController): AchievementGui = new AchievementGui(pageController)

  /**
   * Gets the achievement data as a 2D array.
   *
   * @return A 2D array containing the achievement data.
   */
  private def getDataOnArray: Array[Array[AnyRef]] =
    var stringArray: Array[Array[AnyRef]] = Array[Array[AnyRef]]()

    AchievementController.achievementList.foreach(
      ach =>
        val string: Array[AnyRef] = Array(ach.description, if ach.isAchieved then "Achieved" else "Not achieved")
        stringArray = string +: stringArray
    )
    stringArray
