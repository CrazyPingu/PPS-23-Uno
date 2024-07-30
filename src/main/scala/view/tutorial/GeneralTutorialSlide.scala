package view.tutorial

import java.awt.Image

class GeneralTutorialSlide(private val guiImage: Image, private val guiTitle : String, private val guiDescription: String):
  val image: Image = guiImage
  val title: String = guiTitle
  val description: String = guiDescription