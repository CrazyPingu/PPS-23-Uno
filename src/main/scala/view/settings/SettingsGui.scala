package view.settings

import controller.{PageController, SettingsController}
import model.settings.Difficulty.Difficulty
import model.settings.{Difficulty, Settings}
import utils.ImageHandler.backgroundSettings
import view.{Button, ComboBox, GridBagConstraints, Label}

import java.awt.event.ActionListener
import java.awt.{Graphics, Graphics2D, GridBagLayout, Insets}
import javax.swing.event.{ChangeEvent, ChangeListener}
import javax.swing.{JLabel, JPanel, JSlider}

class SettingsGui private (private val pageController: PageController) extends JPanel:
  setLayout(new GridBagLayout())

  add(SettingsGui.difficultyLabel, new GridBagConstraints(0, 0, 1, SettingsGui.defaultInsets))
  add(SettingsGui.difficultyOptions, new GridBagConstraints(1, 0, 2, SettingsGui.defaultInsets))
  add(SettingsGui.startCardLabel, new GridBagConstraints(0, 1, 1, SettingsGui.defaultInsets))
  add(SettingsGui.startCardSlider, new GridBagConstraints(1, 1, 1, SettingsGui.defaultInsets))
  add(SettingsGui.startCardValue, new GridBagConstraints(2, 1, 1, SettingsGui.defaultInsets))
  add(SettingsGui.handicapLabel, new GridBagConstraints(0, 2, 1, SettingsGui.defaultInsets))
  add(SettingsGui.handicapSlider, new GridBagConstraints(1, 2, 1, SettingsGui.defaultInsets))
  add(SettingsGui.handicapValue, new GridBagConstraints(2, 2, 1, SettingsGui.defaultInsets))
  add(SettingsGui.saveSettings, new GridBagConstraints(0, 3, 1, SettingsGui.defaultInsets))
  add(SettingsGui.resetSettings, new GridBagConstraints(1, 3, 1, SettingsGui.defaultInsets))
  add(SettingsGui.goBackButton, new GridBagConstraints(2, 3, 1, SettingsGui.defaultInsets))

  connectSliderToLabel(SettingsGui.startCardSlider, SettingsGui.startCardValue)
  connectSliderToLabel(SettingsGui.handicapSlider, SettingsGui.handicapValue)
  updateWithSavedSettings()

  SettingsGui.saveSettings.addActionListener(
    _ =>
      val newSettings: Settings = Settings(
        Difficulty.fromInt(SettingsGui.difficultyOptions.getSelectedIndex),
        SettingsGui.startCardSlider.getValue,
        SettingsGui.handicapSlider.getValue
      )
      SettingsController.saveSettings(newSettings)
  )

  SettingsGui.resetSettings.addActionListener(
    _ =>
      SettingsController.resetSettings()
      updateWithSavedSettings()
  )

  SettingsGui.goBackButton.addActionListener(
    _ => pageController.showMainMenu()
  )

  private def connectSliderToLabel(slider: JSlider, label: JLabel): Unit =
    slider.addChangeListener(
      (e: ChangeEvent) =>
        val slider: JSlider = e.getSource.asInstanceOf[JSlider]
        label.setText(slider.getValue.toString)
    )

  private def updateWithSavedSettings(): Unit =
    val currentSettings: Settings = SettingsController.getCurrentSettings
    SettingsGui.difficultyOptions.setSelectedIndex(Difficulty.toInt(currentSettings.difficulty))
    SettingsGui.startCardSlider.setValue(currentSettings.startCardValue)
    SettingsGui.handicapSlider.setValue(currentSettings.handicap)

  override protected def paintComponent(g: Graphics): Unit =
    super.paintComponent(g)
    val g2d: Graphics2D = g.asInstanceOf[Graphics2D]
    g2d.drawImage(backgroundSettings, 0, 0, getWidth, getHeight, this)

object SettingsGui:
  private val MIN_STARTING_CARDS: Int = 4
  private val MAX_STARTING_CARDS: Int = 7
  private val DIFFICULTY_LIST: Array[Difficulty] = Difficulty.values.toArray
  private val defaultInsets: Insets = new Insets(10, 10, 10, 10)
  private val difficultyLabel: Label = new Label("Difficulty:")
  private val difficultyOptions: ComboBox = new ComboBox(DIFFICULTY_LIST.map(_.toString))
  private val startCardLabel: Label = new Label("Number of starting cards:")
  private val startCardSlider: JSlider = new JSlider(4, 7, 7)
  private val startCardValue: Label = new Label("7")
  private val handicapLabel: Label = new Label("Bot handicap:")
  private val handicapSlider: JSlider = new JSlider(-3, 3, 0)
  private val handicapValue: Label = new Label("0")
  private val saveSettings: Button = new Button("Save settings")
  private val resetSettings: Button = new Button("Reset settings")
  private val goBackButton: Button = new Button("Go Back to Menu")

  def apply(pageController: PageController): SettingsGui = new SettingsGui(pageController)
