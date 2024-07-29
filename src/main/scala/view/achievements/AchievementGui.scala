package view.achievements

import controller.{AchievementController, PageController}
import utils.ImageHandler.backgroundAchievements
import view.settings.Table
import view.{Button, GridBagConstraints, Label}

import java.awt.{Color, Graphics, Graphics2D, GridBagLayout, Insets}
import javax.swing.{JPanel, JTable}

class AchievementGui(pageController: PageController, achievementController: AchievementController) extends JPanel:
  setLayout(new GridBagLayout())
  private val defaultInsets: Insets = new Insets(10, 10, 10, 10)

  private val title: Label = new Label("Achievements:")
  title.setForeground(Color.BLACK)
  add(title, new GridBagConstraints(0, 0, 2, defaultInsets))

  private val columnNames: Array[AnyRef] = Array("Description", "Is achieved")
  private var achievementTable: Table = new Table(getDataOnArray, columnNames, Array(400, 200))
  add(achievementTable, new GridBagConstraints(0, 1, 2, defaultInsets))

  private val resetAchievementsButton: Button = new Button("Reset achievements")
  add(resetAchievementsButton, new GridBagConstraints(0, 2, 1, defaultInsets))

  private val goBackButton: Button = new Button("Go back to menu")
  add(goBackButton, new GridBagConstraints(1, 2, 1, defaultInsets))

  resetAchievementsButton.addActionListener(
    _ =>
      achievementController.resetAchievements()
      pageController.showMainMenu()
  )

  goBackButton.addActionListener(
    _ => pageController.showMainMenu()
  )

  def updateGui(): Unit =
    remove(achievementTable)
    achievementTable = new Table(getDataOnArray, columnNames, Array(400, 200))
    add(achievementTable, new GridBagConstraints(0, 1, 2, defaultInsets))

  private def getDataOnArray: Array[Array[AnyRef]] =
    var stringArray: Array[Array[AnyRef]] = Array[Array[AnyRef]]()

    achievementController.achievementList.foreach( ach =>
      val string: Array[AnyRef] = Array(ach.description, prettyBoolean(ach.isAchieved))
      stringArray = string +: stringArray
    )
    stringArray

  private def prettyBoolean(boolean: Boolean): String =
    if boolean then "Achieved"
    else "Not achieved"

  override protected def paintComponent(g: Graphics): Unit =
    super.paintComponent(g)
    val g2d: Graphics2D = g.asInstanceOf[Graphics2D]
    g2d.drawImage(backgroundAchievements, 0, 0, getWidth, getHeight, this)
