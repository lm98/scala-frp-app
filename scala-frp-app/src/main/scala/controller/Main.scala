package controller

import monix.execution.Scheduler.Implicits.global
import view.ui.STUI

@main def main(): Unit =
  val ui = new STUI(800,800)
  val loop = GameLoop.start(ui)
  loop.runAsyncAndForget

