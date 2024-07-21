package view.settings

import model.settings.{GameSettings, Settings, SettingsImpl}
import view.game.CoordinateHandler.panelGridDimension

import java.awt.event.{ActionEvent, ActionListener}
import java.awt.{Component, Dimension, FlowLayout, GridLayout}
import javax.swing.{Box, JButton, JPanel}

class SettingsGui extends JPanel:
  private val layout: GridLayout = new GridLayout(panelGridDimension(0), panelGridDimension(1))
  setLayout(layout)

  private val projectRoot: String = System.getProperty("user.dir")
  private val settingsFilePath: String = s"$projectRoot/config/settings.json"

  private val settings : Settings = SettingsImpl(settingsFilePath)

  private val difficultyButton: JButton = new JButton("Difficulty")
  private val button2: JButton = new JButton("Button 2")
  private val goBackButton: JButton = new JButton("Go Back to Menu")

  private val buttonDimension = new Dimension(200, 100)
  difficultyButton.setPreferredSize(buttonDimension)
  button2.setPreferredSize(buttonDimension)
  goBackButton.setPreferredSize(buttonDimension)
  
  goBackButton.addActionListener((e: ActionEvent) => println("Go Back to Menu button pressed"))

  private def createCenteredPanel(componentList: List[Component]): JPanel =
    val panel = new JPanel(new FlowLayout(FlowLayout.CENTER))
    componentList.foreach(panel.add)
    panel
