package view.settings

import controller.{PageController, SettingsController}
import model.settings.Difficulty.Difficulty
import model.settings.{Difficulty, GameSettings}
import utils.ImageHandler.backgroundSettings
import view.{Button, ComboBox, GridBagConstraints, Label}

import java.awt.event.ActionListener
import java.awt.{Graphics, Graphics2D, GridBagLayout, Insets}
import javax.swing.event.{ChangeEvent, ChangeListener}
import javax.swing.{JLabel, JPanel, JSlider}

object SettingsGui extends JPanel:
  private val MIN_STARTING_CARDS: Int = 4
  private val MAX_STARTING_CARDS: Int = 7
  private val DIFFICULTY_LIST: Array[Difficulty] = Difficulty.values.toArray

  setLayout(new GridBagLayout())
  private val defaultInsets: Insets = new Insets(10, 10, 10, 10)

  private val difficultyLabel: Label = new Label("Difficulty:")
  add(difficultyLabel, new GridBagConstraints(0, 0, 1, defaultInsets))

  private val difficultyOptions: ComboBox = new ComboBox(DIFFICULTY_LIST.map(_.toString))
  add(difficultyOptions, new GridBagConstraints(1, 0, 2, defaultInsets))

  private val startCardLabel: Label = new Label("Number of starting cards:")
  add(startCardLabel, new GridBagConstraints(0, 1, 1, defaultInsets))

  private val startCardSlider: JSlider = new JSlider(4, 7, 7)
  add(startCardSlider, new GridBagConstraints(1, 1, 1, defaultInsets))

  private val startCardValue: Label = new Label("7")
  add(startCardValue, new GridBagConstraints(2, 1, 1, defaultInsets))

  private val handicapLabel: Label = new Label("Bot handicap:")
  add(handicapLabel, new GridBagConstraints(0, 2, 1, defaultInsets))

  private val handicapSlider: JSlider = new JSlider(-3, 3, 0)
  add(handicapSlider, new GridBagConstraints(1, 2, 1, defaultInsets))

  private val handicapValue: Label = new Label("0")
  add(handicapValue, new GridBagConstraints(2, 2, 1, defaultInsets))

  private val saveSettings: Button = new Button("Save settings")
  add(saveSettings, new GridBagConstraints(0, 3, 1, defaultInsets))

  private val resetSettings: Button = new Button("Reset settings")
  add(resetSettings, new GridBagConstraints(1, 3, 1, defaultInsets))

  private val goBackButton: Button = new Button("Go Back to Menu")
  add(goBackButton, new GridBagConstraints(2, 3, 1, defaultInsets))

  connectSliderToLabel(startCardSlider, startCardValue)
  connectSliderToLabel(handicapSlider, handicapValue)
  updateWithSavedSettings()

  saveSettings.addActionListener(
    _ =>
      val newSettings: GameSettings = GameSettings(
        Difficulty.fromInt(difficultyOptions.getSelectedIndex),
        startCardSlider.getValue,
        handicapSlider.getValue
      )
      SettingsController.saveSettings(newSettings)
  )

  resetSettings.addActionListener(
    _ =>
      SettingsController.resetSettings()
      updateWithSavedSettings()
  )

  goBackButton.addActionListener(
    _ => PageController.showMainMenu()
  )

  private def connectSliderToLabel(slider: JSlider, label: JLabel): Unit =
    slider.addChangeListener(
      (e: ChangeEvent) =>
        val slider: JSlider = e.getSource.asInstanceOf[JSlider]
        label.setText(slider.getValue.toString)
    )

  private def updateWithSavedSettings(): Unit =
    val currentSettings: GameSettings = SettingsController.settings.gameSettings
    difficultyOptions.setSelectedIndex(Difficulty.toInt(currentSettings.difficulty))
    startCardSlider.setValue(currentSettings.startCardValue)
    handicapSlider.setValue(currentSettings.handicap)

  override protected def paintComponent(g: Graphics): Unit =
    super.paintComponent(g)
    val g2d: Graphics2D = g.asInstanceOf[Graphics2D]
    g2d.drawImage(backgroundSettings, 0, 0, getWidth, getHeight, this)
