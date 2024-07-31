package view

import controller.PageController
import utils.ImageHandler.{backgroundTable, gameLogo}
import view.{Button, GridBagConstraints}

import java.awt.GridBagLayout
import java.awt.event.ActionListener
import javax.swing.*

object Mainmenu extends JPanel:
  setLayout(new GridBagLayout())

  this.add(
    new JLabel(new ImageIcon(gameLogo.getScaledInstance(400, 300, java.awt.Image.SCALE_SMOOTH))),
    new GridBagConstraints(0, 0)
  )

  private val startButton = new Button("New Game", (200, 50))
  this.add(startButton, new GridBagConstraints(0, 1, 10, 10))

  private val tutorialButton = new Button("Tutorial", (200, 50))
  this.add(tutorialButton, new GridBagConstraints(0, 2, 10, 10))

  private val achievementButton = new Button("Achievements", (200, 50))
  this.add(achievementButton, new GridBagConstraints(0, 3, 10, 10))

  private val settingsButton = new Button("Settings", (200, 50))
  this.add(settingsButton, new GridBagConstraints(0, 4, 10, 10))

  private val exitButton = new Button("Exit", (200, 50))
  this.add(exitButton, new GridBagConstraints(0, 5, 10, 10))

  startButton.addActionListener(
    _ => PageController.showGame()
  )

  tutorialButton.addActionListener(
    _ => PageController.showTutorial()
  )

  achievementButton.addActionListener(
    _ => PageController.showAchievements()
  )

  settingsButton.addActionListener(
    _ => PageController.showSettings()
  )

  exitButton.addActionListener(
    _ => PageController.closeWindow()
  )

  override def paintComponent(g: java.awt.Graphics): Unit =
    super.paintComponent(g)
    g.drawImage(backgroundTable, 0, 0, this.getWidth, this.getHeight, this)
