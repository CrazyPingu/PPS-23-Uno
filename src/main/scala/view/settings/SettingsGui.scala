package view.settings

import controller.SettingsController
import model.settings.Difficulty.Difficulty
import model.settings.{Difficulty, GameSettings}
import utils.ImageHandler.backgroundSettings
import view.game.CoordinateHandler.panelGridDimension

import java.awt.event.{ActionEvent, ActionListener}
import java.awt.{Color, Component, FlowLayout, Graphics, Graphics2D, GridLayout, LayoutManager}
import javax.swing.event.{ChangeEvent, ChangeListener}
import javax.swing.{Box, JButton, JComboBox, JLabel, JPanel, JSlider}

class SettingsGui(controller: SettingsController) extends JPanel:
  private val MIN_STARTING_CARDS: Int = 4
  private val MAX_STARTING_CARDS: Int = 7
  private val DIFFICULTY_LIST: Array[Difficulty] = Difficulty.values.toArray

  private val layout: GridLayout = new GridLayout(panelGridDimension(0), panelGridDimension(1))
  setLayout(layout)
  
  add(Box.createVerticalGlue())

  private val difficultyLabel : JLabel = new JLabel("Difficulty:")
  private val difficultyOptions: JComboBox[String] = new JComboBox(DIFFICULTY_LIST.map(_.toString))
  add(generatePanel(List(difficultyLabel, difficultyOptions), None))

  add(Box.createVerticalStrut(10))

  private val startCardLabel : JLabel = new JLabel("Number of starting cards:")
  private val startCardSlider: JSlider = new JSlider(4, 7, 7)
  private val startCardValue: JLabel = new JLabel("7")
  add(generatePanel(List(startCardLabel, startCardSlider, startCardValue), None))

  add(Box.createVerticalStrut(20))

  private val saveSettings : JButton = new JButton("Save settings")
  private val goBackButton: JButton = new JButton("Go Back to Menu")
  add(generatePanel(List(saveSettings, goBackButton), Option(new FlowLayout())))

  add(Box.createVerticalGlue())

  startCardSlider.addChangeListener((e: ChangeEvent) =>
    val slider: JSlider = e.getSource.asInstanceOf[JSlider]
    startCardValue.setText(slider.getValue.toString)
  )

  saveSettings.addActionListener((e: ActionEvent) =>
    val newSettings: GameSettings = GameSettings(
      Difficulty.fromInt(difficultyOptions.getSelectedIndex),
      startCardSlider.getValue
    )
    
  )

  goBackButton.addActionListener((e: ActionEvent) =>
    println("Go Back to Menu button pressed")
  )

  private def generatePanel(componentList: List[Component], layout: Option[LayoutManager]): JPanel =
    val panel: JPanel = new JPanel()
    panel.setForeground(Color.WHITE)
    if layout.isEmpty then
      panel.setLayout(new FlowLayout())
    else
      panel.setLayout(layout.get)
    panel.setOpaque(false)
    componentList.foreach(panel.add)
    panel

  override protected def paintComponent(g: Graphics): Unit =
    super.paintComponent(g)
    val g2d: Graphics2D = g.asInstanceOf[Graphics2D]
    g2d.drawImage(backgroundSettings, 0, 0, getWidth, getHeight, this)