package view.tutorial

import controller.PageController

import javax.swing.JPanel
import java.awt.*
import javax.swing.*
import javax.swing.border.EmptyBorder
import view.{Button, GridBagConstraints}

/**
 * A class representing the tutorial GUI.
 *
 * @param pageController The page controller.
 */
class TutorialGui private (private val pageController: PageController) extends JPanel:

  this.setBackground(new Color(10, 10, 10))

  TutorialGui.buttonPanel.setBackground(new Color(10, 10, 10))
  TutorialGui.buttonPanel.add(TutorialGui.prevButton, new GridBagConstraints(0, 0, 1, TutorialGui.defaultInsets))
  TutorialGui.buttonPanel.add(TutorialGui.mainMenuButton, new GridBagConstraints(0, 1, 2, TutorialGui.defaultInsets))
  TutorialGui.buttonPanel.add(TutorialGui.nextButton, new GridBagConstraints(1, 0, 1, TutorialGui.defaultInsets))

  TutorialGui.titleLabel.setFont(new Font("Arial", Font.BOLD, 40))
  TutorialGui.titleLabel.setForeground(Color.WHITE)

  TutorialGui.descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 25))
  TutorialGui.descriptionLabel.setForeground(Color.WHITE)

  TutorialGui.textPanel.setBackground(new Color(10, 10, 10))
  TutorialGui.textPanel.add(TutorialGui.titleLabel)
  TutorialGui.textPanel.add(TutorialGui.descriptionLabel)

  this.setLayout(new BorderLayout())
  this.setBorder(new EmptyBorder(25, 25, 25, 25))
  this.add(TutorialGui.textPanel, BorderLayout.NORTH)
  this.add(TutorialGui.imageLabel, BorderLayout.CENTER)
  this.add(TutorialGui.buttonPanel, BorderLayout.SOUTH)

  TutorialGui.prevButton.addActionListener(
    _ => TutorialGui.showSlide(TutorialGui.currentSlideIndex - 1)
  )
  TutorialGui.nextButton.addActionListener(
    _ => TutorialGui.showSlide(TutorialGui.currentSlideIndex + 1)
  )
  TutorialGui.mainMenuButton.addActionListener(
    _ =>
      pageController.showMainMenu()
      TutorialGui.showSlide(0)
  )
  TutorialGui.showSlide(0)

object TutorialGui:
  private var currentSlideIndex = 0
  private val SlideFactory = new TutorialSlideFactory()
  private val slides = Array(
    SlideFactory.createCompatibilitySlide(),
    SlideFactory.createSpecialCardSlide(),
    SlideFactory.createCardSlide(),
    SlideFactory.createDeckSlide(),
    SlideFactory.createUnoSlide(),
    SlideFactory.createWinLoseSlide()
  )
  private val imageLabel = new JLabel("", SwingConstants.CENTER)
  private val titleLabel = new JLabel("", SwingConstants.CENTER)

  private val prevButton = new Button("Previous Page", (200, 50))
  private val nextButton = new Button("Next Page", (200, 50))
  private val mainMenuButton = new Button("Main Menu", (410, 50))
  private val defaultInsets: Insets = new Insets(5, 5, 5, 5)
  private val buttonPanel = new JPanel(new GridBagLayout())
  private val descriptionLabel = new JLabel("", SwingConstants.CENTER)
  private val textPanel = new JPanel(new GridLayout(2, 1))
  private def showSlide(index: Int): Unit =
    if index >= 0 && index < slides.length then
      prevButton.setEnabled(index != 0)
      nextButton.setEnabled(index != slides.length - 1)
      currentSlideIndex = index
      val slide = slides(index)
      titleLabel.setText(slide.title)
      imageLabel.setIcon(new ImageIcon(slide.image))
      descriptionLabel.setText(slide.description)

  def apply(pageController: PageController): TutorialGui = new TutorialGui(pageController)
