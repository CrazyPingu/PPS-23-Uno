package view.settings

import controller.{PageController, SettingsController}
import model.settings.Difficulty.Difficulty
import model.settings.{Difficulty, GameSettings}
import utils.ImageHandler.backgroundSettings
import view.{Button, GridBagConstraints, Label}

import java.awt.event.{ActionEvent, ActionListener}
import java.awt.{Graphics, Graphics2D, GridBagLayout, Insets}
import javax.swing.event.{ChangeEvent, ChangeListener}
import javax.swing.{JButton, JComboBox, JLabel, JPanel, JSlider}

class SettingsGui(pageController: PageController, controller: SettingsController) extends JPanel:
  private val MIN_STARTING_CARDS: Int = 4
  private val MAX_STARTING_CARDS: Int = 7
  private val DIFFICULTY_LIST: Array[Difficulty] = Difficulty.values.toArray

  private val layout: GridBagLayout = new GridBagLayout()
  private val c: GridBagConstraints = GridBagConstraints()
  setLayout(layout)
  c.insets = new Insets(10, 10, 10, 10)

  c.gridx = 0
  c.gridy = 0
  c.gridwidth = 1
  private val difficultyLabel : Label = new Label("Difficulty:")
  add(difficultyLabel, c)

  c.gridx = 1
  c.gridy = 0
  c.gridwidth = 2
  private val difficultyOptions: JComboBox[String] = new JComboBox(DIFFICULTY_LIST.map(_.toString))
  add(difficultyOptions, c)

  c.gridx = 0
  c.gridy = 1
  c.gridwidth = 1
  private val startCardLabel : Label = new Label("Number of starting cards:")
  add(startCardLabel, c)

  c.gridx = 1
  c.gridy = 1
  c.gridwidth = 1
  private val startCardSlider: JSlider = new JSlider(4, 7, 7)
  add(startCardSlider, c)

  c.gridx = 2
  c.gridy = 1
  c.gridwidth = 1
  private val startCardValue: Label = new Label("7")
  add(startCardValue, c)

  c.gridx = 0
  c.gridy = 2
  c.gridwidth = 1
  private val handicapLabel: Label = new Label("Bot handicap:")
  add(handicapLabel, c)

  c.gridx = 1
  c.gridy = 2
  c.gridwidth = 1
  private val handicapSlider: JSlider = new JSlider(-3, 3, 0)
  add(handicapSlider, c)

  c.gridx = 2
  c.gridy = 2
  c.gridwidth = 1
  private val handicapValue: Label = new Label("0")
  add(handicapValue, c)

  c.gridx = 0
  c.gridy = 3
  c.gridwidth = 1
  private val saveSettings : Button = new Button("Save settings")
  add(saveSettings, c)

  c.gridx = 1
  c.gridy = 3
  c.gridwidth = 1
  private val resetSettings: Button = new Button("Reset settings")
  add(resetSettings, c)

  c.gridx = 2
  c.gridy = 3
  c.gridwidth = 1
  private val goBackButton: Button = new Button("Go Back to Menu")
  add(goBackButton, c)

  connectSliderToLabel(startCardSlider, startCardValue)
  connectSliderToLabel(handicapSlider, handicapValue)

  saveSettings.addActionListener((e: ActionEvent) =>
    val newSettings: GameSettings = GameSettings(
      Difficulty.fromInt(difficultyOptions.getSelectedIndex),
      startCardSlider.getValue,
      handicapSlider.getValue
    )
    controller.saveSettings(newSettings)
  )

  goBackButton.addActionListener((e: ActionEvent) =>
    pageController.showMainMenu()
  )

  private def connectSliderToLabel(slider: JSlider, label: JLabel): Unit =
    slider.addChangeListener((e: ChangeEvent) =>
      val slider: JSlider = e.getSource.asInstanceOf[JSlider]
      label.setText(slider.getValue.toString)
    )

  override protected def paintComponent(g: Graphics): Unit =
    super.paintComponent(g)
    val g2d: Graphics2D = g.asInstanceOf[Graphics2D]
    g2d.drawImage(backgroundSettings, 0, 0, getWidth, getHeight, this)