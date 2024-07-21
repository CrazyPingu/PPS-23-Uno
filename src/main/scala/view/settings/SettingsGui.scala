package view.settings

import model.settings.{GameSettings, Settings, SettingsImpl}
import view.game.CoordinateHandler.panelGridDimension

import java.awt.event.{ActionEvent, ActionListener}
import java.awt.GridLayout
import javax.swing.{Box, JButton, JComboBox, JLabel, JPanel}

class SettingsGui extends JPanel:
  private val layout: GridLayout = new GridLayout(panelGridDimension(0), panelGridDimension(1))
  setLayout(layout)

  private val projectRoot: String = System.getProperty("user.dir")
  private val settingsFilePath: String = s"$projectRoot/config/settings.json"
  private val settings : Settings = SettingsImpl(settingsFilePath)

  add(Box.createVerticalGlue())

  private val difficultyPanel : JPanel = new JPanel()
  private val difficultyLabel : JLabel = new JLabel("Difficulty")
  private val difficultyOptions: JComboBox[String] = new JComboBox(Array("Easy", "Medium", "Hard"))
  difficultyPanel.add(difficultyLabel)
  difficultyPanel.add(difficultyOptions)
  add(difficultyPanel)

  add(Box.createVerticalStrut(10))

  private val button2Panel : JPanel = new JPanel()
  private val button2Label : JLabel = new JLabel("Button 2")
  private val button2Options: JComboBox[String] = new JComboBox(Array("Option 1", "Option 2", "Option 3"))
  button2Panel.add(button2Label)
  button2Panel.add(button2Options)
  add(button2Panel)

  add(Box.createVerticalStrut(20))

  private val endPanel : JPanel = new JPanel()
  private val saveSettings : JButton = new JButton("Save settings")
  private val goBackButton: JButton = new JButton("Go Back to Menu")
  endPanel.add(saveSettings)
  endPanel.add(goBackButton)
  add(endPanel)

  add(Box.createVerticalGlue())

  saveSettings.addActionListener((e: ActionEvent) => settings.updateSettings(GameSettings(
    difficultyOptions.getSelectedItem.toString
    )))

  goBackButton.addActionListener((e: ActionEvent) =>
    println("Go Back to Menu button pressed"))

  //override protected def paintComponent(g: Graphics): Unit =
  //  super.paintComponent(g)
  //  g.setColor(Color.RED)
  //  g.fillRect(0, 0, getWidth, getHeight)
