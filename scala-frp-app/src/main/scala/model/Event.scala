package model

/** Anything that can happen in the Universe, whether it be internal logic or User input */
enum Event:
  case TimeElapsed(dt: Double)
  case MovePlayer(pos: Universe.Point2D)
  case Empty
