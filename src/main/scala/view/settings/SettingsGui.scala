package view.settings

import model.settings.{GameSettings, Settings, SettingsImpl}
import utils.ImageHandler.{backgroundSettings, backgroundTable}
import view.game.CoordinateHandler.panelGridDimension

import java.awt.event.{ActionEvent, ActionListener}
import java.awt.{Color, FlowLayout, Graphics, Graphics2D, GridLayout}
import javax.swing.{Box, JButton, JComboBox, JLabel, JPanel}

class SettingsGui extends JPanel:
  private val layout: GridLayout = new GridLayout(panelGridDimension(0), panelGridDimension(1))
  setLayout(layout)

  private val projectRoot: String = System.getProperty("user.dir")
  private val settingsFilePath: String = s"$projectRoot/config/settings.json"
  private val settings : Settings = SettingsImpl(settingsFilePath)

  add(Box.createVerticalGlue())

  private val difficultyPanel : JPanel = new JPanel()
  private val difficultyLabel : JLabel = new JLabel("Difficulty:")
  private val difficultyOptions: JComboBox[String] = new JComboBox(Array("Easy", "Hard"))
  difficultyLabel.setForeground(Color.WHITE)
  difficultyPanel.setLayout(new FlowLayout())
  difficultyPanel.setOpaque(false)
  difficultyPanel.add(difficultyLabel)
  difficultyPanel.add(difficultyOptions)
  add(difficultyPanel)

  add(Box.createVerticalStrut(10))

  private val buttonPanel : JPanel = new JPanel()
  private val buttonLabel : JLabel = new JLabel("Button:")
  private val buttonOptions: JComboBox[String] = new JComboBox(Array("Option 1", "Option 2", "Option 3"))
  buttonLabel.setForeground(Color.WHITE)
  buttonPanel.setLayout(new FlowLayout())
  buttonPanel.setOpaque(false)
  buttonPanel.add(buttonLabel)
  buttonPanel.add(buttonOptions)
  add(buttonPanel)

  add(Box.createVerticalStrut(20))

  private val endPanel : JPanel = new JPanel()
  private val saveSettings : JButton = new JButton("Save settings")
  private val goBackButton: JButton = new JButton("Go Back to Menu")
  endPanel.setLayout(new FlowLayout())
  endPanel.setOpaque(false)
  endPanel.add(saveSettings)
  endPanel.add(goBackButton)
  add(endPanel)

  add(Box.createVerticalGlue())

  saveSettings.addActionListener((e: ActionEvent) => settings.updateSettings(GameSettings(
    difficultyOptions.getSelectedItem.toString
    )))

  goBackButton.addActionListener((e: ActionEvent) =>
    println("Go Back to Menu button pressed"))

  override protected def paintComponent(g: Graphics): Unit =
    super.paintComponent(g)
    val g2d: Graphics2D = g.asInstanceOf[Graphics2D]
    g2d.drawImage(backgroundSettings, 0, 0, getWidth, getHeight, this)
