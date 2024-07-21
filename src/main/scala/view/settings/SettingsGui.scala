package view.settings

import model.settings.{GameSettings, Settings, SettingsImpl}
import view.game.CoordinateHandler.panelGridDimension

import java.awt.event.{ActionEvent, ActionListener}
import java.awt.{Color, FlowLayout, Graphics, GridLayout}
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
  private val difficultyOptions: JComboBox[String] = new JComboBox(Array("Easy", "Hard"))
  difficultyPanel.setLayout(new FlowLayout())
  difficultyPanel.add(difficultyLabel)
  difficultyPanel.add(difficultyOptions)
  add(difficultyPanel)

  add(Box.createVerticalStrut(10))

  private val buttonPanel : JPanel = new JPanel()
  private val buttonLabel : JLabel = new JLabel("Button")
  private val buttonOptions: JComboBox[String] = new JComboBox(Array("Option 1", "Option 2", "Option 3"))
  buttonPanel.setLayout(new FlowLayout())
  buttonPanel.add(buttonLabel)
  buttonPanel.add(buttonOptions)
  add(buttonPanel)

  add(Box.createVerticalStrut(20))

  private val endPanel : JPanel = new JPanel()
  private val saveSettings : JButton = new JButton("Save settings")
  private val goBackButton: JButton = new JButton("Go Back to Menu")
  endPanel.setLayout(new FlowLayout())
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
