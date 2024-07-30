package view.tutorial

import controller.PageController

import javax.swing.JPanel
import java.awt.*
import javax.swing.*
import javax.swing.border.EmptyBorder
import view.{Button, GridBagConstraints}

class TutorialGui(pageController: PageController) extends JPanel :
  private val slides = new TutorialSlideFactory().createSlides()
  this.setBackground(new Color(10,10,10))
  private var currentSlideIndex = 0

  private val imageLabel = new JLabel("", SwingConstants.CENTER)

  private val prevButton = new Button("Previous Page")
  private val nextButton = new Button("Next Page")
  private val mainMenuButton = new Button("Main Menu")
  private val defaultInsets: Insets = new Insets(5, 5, 5, 5)
  private val buttonPanel = new JPanel()
  buttonPanel.setBackground(new Color(10,10,10))
  buttonPanel.setLayout(new GridBagLayout())
  buttonPanel.add(prevButton, new GridBagConstraints(0, 0, 1, defaultInsets))
  buttonPanel.add(mainMenuButton, new GridBagConstraints(0, 1, 2, defaultInsets))
  buttonPanel.add(nextButton, new GridBagConstraints(1, 0, 1, defaultInsets))

  private val titleLabel = new JLabel("", SwingConstants.CENTER)
  titleLabel.setFont(new Font("Arial", Font.BOLD, 40))
  titleLabel.setForeground(Color.WHITE)
  
  private val descriptionLabel = new JLabel("", SwingConstants.CENTER)
  descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 25))
  descriptionLabel.setForeground(Color.WHITE)
  
  private val textPanel = new JPanel()
  textPanel.setBackground(new Color(10,10,10))
  textPanel.setLayout(new GridLayout(2, 1))
  textPanel.add(titleLabel)
  textPanel.add(descriptionLabel)

  this.setLayout(new BorderLayout())
  this.setBorder(new EmptyBorder(25, 25, 25, 25))
  this.add(textPanel, BorderLayout.NORTH)
  this.add(imageLabel, BorderLayout.CENTER)
  this.add(buttonPanel, BorderLayout.SOUTH)

  private def showSlide(index: Int): Unit =
    if (index >= 0 && index < slides.length)
      prevButton.setEnabled(index != 0)
      nextButton.setEnabled(index != slides.length - 1)
      currentSlideIndex = index
      val slide = slides(index)
      titleLabel.setText(slide.title)
      imageLabel.setIcon(new ImageIcon(slide.image))
      descriptionLabel.setText(slide.description)

  prevButton.addActionListener(_ => showSlide(currentSlideIndex - 1))
  nextButton.addActionListener(_ => showSlide(currentSlideIndex + 1))
  mainMenuButton.addActionListener(_ =>
    pageController.showMainMenu()
    showSlide(0))

  showSlide(0)