package view.settings

import model.settings.{GameSettings, Settings, SettingsImpl}
import view.game.CoordinateHandler.panelGridDimension

import java.awt.GridLayout
import javax.swing.{JButton, JPanel}

class SettingsGui extends JPanel:
  private val layout: GridLayout = new GridLayout(panelGridDimension(0), panelGridDimension(1))
  setLayout(layout)

  private val projectRoot: String = System.getProperty("user.dir")
  private val settingsFilePath: String = s"$projectRoot/config/settings.json"

  private val settings : Settings = SettingsImpl(settingsFilePath)
  
  private val button1: JButton = new JButton("Button 1")
  private val button2: JButton = new JButton("Button 2")

  // Add the buttons to the panel
  add(button1)
  add(button2)

  

  

