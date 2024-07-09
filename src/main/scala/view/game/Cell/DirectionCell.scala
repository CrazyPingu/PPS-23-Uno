package view.game.Cell

import utils.ImageHandler

import java.awt.Graphics2D
import java.awt.image.BufferedImage

class DirectionCell extends Cell:
  private var _currentImg = ImageHandler.direction
  setIcon(_currentImg)

  def reverseDirection(): Unit =
    val dimension: (Int, Int) = (_currentImg.getWidth(null), _currentImg.getHeight(null))
    val flippedImage = new BufferedImage(dimension(0), dimension(1), BufferedImage.TYPE_INT_ARGB)
    val g2d: Graphics2D = flippedImage.createGraphics()
    g2d.drawImage(_currentImg, 0, 0, dimension(0), dimension(1), dimension(0), 0, 0, dimension(1), null)
    g2d.dispose()
    setIcon(flippedImage)
    _currentImg = flippedImage
