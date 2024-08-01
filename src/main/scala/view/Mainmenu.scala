package view

import controller.PageController
import utils.ImageHandler.{backgroundTable, gameLogo}
import view.{Button, GridBagConstraints}

import java.awt.GridBagLayout
import java.awt.event.ActionListener
import javax.swing.*

class Mainmenu private (private val pageController: PageController) extends JPanel:
  setLayout(new GridBagLayout())

  this.add(
    new JLabel(new ImageIcon(gameLogo.getScaledInstance(400, 300, java.awt.Image.SCALE_SMOOTH))),
    new GridBagConstraints(0, 0)
  )

  this.add(Mainmenu.startButton, new GridBagConstraints(0, 1, 10, 10))

  this.add(Mainmenu.tutorialButton, new GridBagConstraints(0, 2, 10, 10))

  this.add(Mainmenu.achievementButton, new GridBagConstraints(0, 3, 10, 10))

  this.add(Mainmenu.settingsButton, new GridBagConstraints(0, 4, 10, 10))

  this.add(Mainmenu.exitButton, new GridBagConstraints(0, 5, 10, 10))

  Mainmenu.startButton.addActionListener(
    _ => pageController.showGame()
  )

  Mainmenu.tutorialButton.addActionListener(
    _ => pageController.showTutorial()
  )

  Mainmenu.achievementButton.addActionListener(
    _ => pageController.showAchievements()
  )

  Mainmenu.settingsButton.addActionListener(
    _ => pageController.showSettings()
  )

  Mainmenu.exitButton.addActionListener(
    _ => pageController.closeWindow()
  )

  override def paintComponent(g: java.awt.Graphics): Unit =
    super.paintComponent(g)
    g.drawImage(backgroundTable, 0, 0, this.getWidth, this.getHeight, this)

object Mainmenu:
  private val startButton = new Button("New Game", (200, 50))
  private val tutorialButton = new Button("Tutorial", (200, 50))
  private val achievementButton = new Button("Achievements", (200, 50))
  private val settingsButton = new Button("Settings", (200, 50))
  private val exitButton = new Button("Exit", (200, 50))

  def apply(pageController: PageController): Mainmenu =
    new Mainmenu(pageController)
