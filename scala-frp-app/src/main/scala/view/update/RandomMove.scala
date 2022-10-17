package view.update

import model.Entity.Player
import model.Event.TimeElapsed
import model.Universe.Point2D
import monix.eval.Task
import view.update.Update.*
import monocle.syntax.all.*
import scala.util.Random

object RandomMove:
  def apply()(using random: Random): Update = on[TimeElapsed] { (_, world) =>
    val updates = moveRandom(world.player)
    for
      elements <- updates
    yield world.focus(_.player).replace(elements)
  }

  def moveRandom(player: Player)(using random: Random): Task[Player] = Task {
    player.focus(_.pos).modify(_ + Point2D(random.between(-1, 1), random.between(-1, 1)))
  }

