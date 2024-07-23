package view

import controller.PageController

import java.awt.{GridBagLayout, Image, Insets}
import javax.swing.JPanel
import utils.ImageHandler.{defeatBackground, winBackground}

abstract class EndGameScreen(private val backgroundImage: Image, private val pageController: PageController)
    extends JPanel:

  this.setLayout(new GridBagLayout())
  private val gbc = new java.awt.GridBagConstraints()
  gbc.insets = new Insets(200, 0, 0, 0)

  private val button = new Button("Return to Main Menu", (300, 50))
  this.add(button, gbc)

  button.addActionListener(
    _ => pageController.showMainMenu()
  )

  override def paintComponent(g: java.awt.Graphics): Unit =
    super.paintComponent(g)
    g.drawImage(backgroundImage, 0, 0, this.getWidth, this.getHeight, this)

class WinScreen(private val pageController: PageController) extends EndGameScreen(winBackground, pageController)

class LoseScreen(private val pageController: PageController) extends EndGameScreen(defeatBackground, pageController)