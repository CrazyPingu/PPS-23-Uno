package utils

import java.awt.Image
import java.awt.geom.AffineTransform
import java.awt.image.{AffineTransformOp, BufferedImage}
import java.io.{File, IOException}
import javax.imageio.ImageIO

enum Rotation:
  case NONE, FLIP_HORIZONTAL, FLIP_VERTICAL, ROTATE_RIGHT, ROTATE_LEFT

object ImageHandler:

  val retroCards: Image = loadImage("cards/Retro.png")

  val backgroundTable: Image = loadImage("Table.png")

  val backgroundSettings: Image = loadImage("Settings.png")

  val unoButton: Image = loadImage("cards/UnoButton.png")

  val direction: Image = loadImage("Direction.png")

  val winBackground: Image = loadImage("Win.png")
  val defeatBackground: Image = loadImage("Defeat.png")

  val check: Image = loadImage("cards/Check.png")

  val gameLogo: Image = loadImage("Logo.png")

  val turnArrow: Image = loadImage("Arrow.png")

  def loadCardImage(cardName: String, color: Color): Image =
    loadImage("cards/" + color.toString + "/" + color.toString + "_" + cardName + ".png")

  def rotateImage(img: Image, rotation: Rotation): Image =
    if rotation == Rotation.NONE then img
    else
      val width = img.getWidth(null)
      val height = img.getHeight(null)
      val tx = rotation match
        case Rotation.ROTATE_RIGHT =>
          val tx = AffineTransform.getRotateInstance(Math.toRadians(90), width / 2.0, height / 2.0)
          tx.translate((height - width) / 2.0, (height - width) / 2.0)
          tx
        case Rotation.ROTATE_LEFT =>
          val tx = AffineTransform.getRotateInstance(Math.toRadians(-90), width / 2.0, height / 2.0)
          tx.translate((width - height) / 2.0, (width - height) / 2.0)
          tx
        case Rotation.FLIP_HORIZONTAL =>
          val tx = AffineTransform.getScaleInstance(-1, 1)
          tx.translate(-width, 0)
          tx
        case Rotation.FLIP_VERTICAL =>
          val tx = AffineTransform.getScaleInstance(1, -1)
          tx.translate(0, -height)
          tx
        case _ => new AffineTransform()
      val op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR)
      op.filter(toBufferedImage(img), null)

  private def toBufferedImage(img: Image): BufferedImage =
    img match
      case image: BufferedImage => image
      case _ =>
        val bimage =
          new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB)
        val bGr = bimage.createGraphics()
        bGr.drawImage(img, 0, 0, null)
        bGr.dispose()
        bimage

  private def loadImage(path: String): Image =
    try
      val stream = getClass.getClassLoader.getResourceAsStream(path)
      if stream == null then throw new IOException(s"Resource not found: $path")
      ImageIO.read(stream)
    catch
      case e: IOException =>
        System.err.println(s"Error loading image: $path")
        e.printStackTrace()
        null
