package view

import java.awt.CardLayout
import javax.swing.{JFrame, JPanel, WindowConstants}

enum CardLayoutId:
  case Game, MainMenu, Settings, Rules, Win, Lose, ChangeColor

class Frame extends JFrame:
  private val _cardLayout: CardLayout = new CardLayout
  private val _panel: JPanel = new JPanel(_cardLayout)
  setTitle("PPS-23-UNO")
  setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  setSize(1280, 720)
  setResizable(false)
  setLocationRelativeTo(null)
  add(_panel)
  setVisible(true)

  def add(panel: JPanel, layoutId: CardLayoutId): Unit = _panel.add(panel, layoutId.toString)

  def show(layoutId: CardLayoutId): Unit = _cardLayout.show(_panel, layoutId.toString)



