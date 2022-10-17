package model

import Universe.*

/** Anything that can be represented in the [[GameWorld]] and lives in the [[Universe]] */
enum Entity(val pos: Point2D):
  case Player(override val pos: Point2D) extends Entity(pos)
