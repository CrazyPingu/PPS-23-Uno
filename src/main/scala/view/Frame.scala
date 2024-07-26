package view

import utils.ImageHandler
import view.CardLayoutId.Base

import java.awt.CardLayout
import javax.swing.{JFrame, JPanel, UIManager, WindowConstants}

/**
 * Enum to handle the different layouts of the frame using CardLayout
 */
enum CardLayoutId:
  case Game, MainMenu, Settings, Tutorial, Achievement, Win, Lose, ChangeColor, Base

/**
 * The frame of the application, that contains all the panels.
 * It uses a CardLayout to switch between the different panels.
 */
class Frame extends JFrame:
  private val cardLayout: CardLayout = new CardLayout
  private val cardPanel: JPanel = new JPanel(cardLayout)
  private var currentLayout: CardLayoutId = _
  setTitle("PPS-23-UNO")
  setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  setSize(1280, 720)
  setResizable(false)
  setLocationRelativeTo(null)
  try
    for info <- UIManager.getInstalledLookAndFeels do
      if "Nimbus" == info.getName then UIManager.setLookAndFeel(info.getClassName)
  catch case _: Exception => ()
  add(cardPanel)
  add(new JPanel(), Base)
  show(Base)
  setVisible(true)

  /**
   * Add a panel to the frame
   *
   * @param panel the panel to add
   * @param layoutId the id of the layout
   */
  def add(panel: JPanel, layoutId: CardLayoutId): Unit = cardPanel.add(panel, layoutId.toString)

  /**
   * Show a panel of the frame
   *
   * @param layoutId the id of the layout
   */
  def show(layoutId: CardLayoutId): Unit =
    cardLayout.show(cardPanel, layoutId.toString)
    currentLayout = layoutId

  /**
   * Check which layout is currently showing
   *
   * @param layoutId the id of the layout
   * @return true if the layout is currently showing, false otherwise
   */
  def isShowing(layoutId: CardLayoutId): Boolean = currentLayout == layoutId
