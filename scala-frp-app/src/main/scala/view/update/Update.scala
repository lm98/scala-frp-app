package view.update

import model.GameWorld
import model.Event
import monix.eval.Task

import scala.reflect.ClassTag

/** An Update is a function from the [[model.GameWorld]] and [[model.Event]] that produces an update Task */
trait Update extends ((Event, GameWorld) => Task[(GameWorld, Update)]):
  /** Apply an update after the current one */
  def andThen(control: Update): Update = (event, world) =>
    this(event, world)
      .flatMap { case (world, left) => control(event, world).map { case (world, right) => (left, right, world) } }
      .map { case (left, right, world) => (world, Update.combineTwo(left, right)) }


object Update:

  def on[E <: Event](control: (E, GameWorld) => Task[GameWorld])(using ClassTag[E]): Update =
    lazy val result: Update = (event, world) =>
      event match
        case event: E => control(event, world).map(world => (world, result))
        case _ => Task((world, result))
    result

  /** Combines two update functions into one */
  def combineTwo(upA: Update, upB: Update): Update = (event, world) =>
    for
      updateA <- upA.apply(event, world)
      (step1, newA) = updateA
      updateB <- upB.apply(event, step1)
      (step2, newB) = updateB
    yield (step2, combineTwo(newA, newB))

  /** Combines many update functions into one */
  def combineMany(updates: Update*): Update =
    updates.reduce(_.andThen(_))