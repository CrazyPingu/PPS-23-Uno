package view.achievements

import controller.{AchievementController, PageController}

import java.awt.GridBagLayout
import javax.swing.JPanel

class AchievementGui(pageController: PageController, achievementController: AchievementController) extends JPanel:
  setLayout(new GridBagLayout())

