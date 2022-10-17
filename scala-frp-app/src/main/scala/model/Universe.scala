package model

/** Encompasses every physical aspect of the game */
object Universe:
  opaque type Point2D = (Double, Double)
  
  object Point2D:
    def apply(x: Double, y: Double): Point2D = (x, y)
    def unapply(point2D: Point2D): Option[(Double, Double)] = Some((point2D._1, point2D._2))
  
  extension (p: Point2D)
    def x = p._1
    def y = p._2
    def +(other: Point2D) = Point2D(p.x + other.x, p.y + other.y)
    def -(other: Point2D) = Point2D(p.x - other.x, p.y - other.y)