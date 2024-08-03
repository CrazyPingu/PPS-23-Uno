package model.cards

import utils.Color

import java.awt.Image

/**
 * Represents a card in the game.
 */
trait Card:

  val color: Color

  val image: Image
