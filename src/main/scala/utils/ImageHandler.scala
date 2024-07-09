package utils

import java.awt.Image
import java.io.{File, IOException}
import javax.imageio.ImageIO

object ImageHandler:

  val retroCards: Image = loadImage("cards/Retro.png")

  val backgroundTable: Image = loadImage("Table.png")

  val unoButton: Image = loadImage("cards/UnoButton.png")

  private def loadImage(path: String): Image =
    try ImageIO.read(new File("src/main/resources/" + path))
    catch
      case e: IOException =>
        System.err.println(s"Error loading image: $path")
        e.printStackTrace()
        null

  def loadCardImage(cardName: String, color: Color): Image =
    loadImage("cards/" + color.toString + "/" + color.toString + "_" + cardName + ".png")
