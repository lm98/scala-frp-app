package controller
import model.{Event, GameWorld, TimeFlow}
import monix.eval.Task
import monix.reactive.{Observable, OverflowStrategy}

import scala.language.postfixOps
import concurrent.duration.DurationInt
import monix.execution.Scheduler.Implicits.global
import view.ui.UI
import view.update.{RandomMove, Update}

import scala.util.Random

object GameLoop:
  private val time: Observable[Event] =
    TimeFlow
      .tickContinually(33 milliseconds)
      .map(_.toDouble)
      .map(Event.TimeElapsed.apply)

  def start(ui: UI): Task[Unit] =
    given Random = Random()
    given OverflowStrategy[Event] = OverflowStrategy.Default
    val world = GameWorld.init
    val controls = Update.combineMany(RandomMove())

    val init = Task(world, controls)
    val events = Observable(time, ui.events.throttleLast(33 milliseconds)).merge

    events
      .scanEval(init) { case ((world, controls), event) => controls(event, world) }
      .doOnNext { case (world, _) => ui.render(world) }
      .completedL