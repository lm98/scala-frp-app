package model

import Entity.*
import model.Universe.Point2D

/** A structure containing every entity that needs to be represented. */
case class GameWorld(player: Player)

object GameWorld:
  /** Initial state of the [[GameWorld]] */
  def init: GameWorld = GameWorld(Player(Point2D(0,0)))
