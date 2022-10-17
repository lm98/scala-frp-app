package view.ui

import model.{Event, GameWorld}
import monix.eval.Task
import monix.reactive.Observable

trait UI:
  /** Events produced by the UI */
  def events: Observable[Event]
  /** Render the current [[GameWorld]].
   * @return a [[Task]] representing the rendering computation. */
  def render(gw: GameWorld): Task[Unit]
