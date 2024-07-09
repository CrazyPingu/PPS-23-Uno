package view

import java.awt.CardLayout
import javax.swing.{JFrame, JPanel, WindowConstants}

/**
 * Enum to handle the different layouts of the frame using CardLayout
 */
enum CardLayoutId:
  case Game, MainMenu, Settings, Rules, Win, Lose, ChangeColor

/**
 * The frame of the application, that contains all the panels.
 * It uses a CardLayout to switch between the different panels.
 */
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

  /**
   * Add a panel to the frame
   *
   * @param panel the panel to add
   * @param layoutId the id of the layout
   */
  def add(panel: JPanel, layoutId: CardLayoutId): Unit = _panel.add(panel, layoutId.toString)

  /**
   * Show a panel of the frame
   *
   * @param layoutId the id of the layout
   */
  def show(layoutId: CardLayoutId): Unit = _cardLayout.show(_panel, layoutId.toString)



