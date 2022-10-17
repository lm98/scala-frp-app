package view.ui
import model.{Event, GameWorld}
import monix.eval.Task
import monix.reactive.Observable

import javax.swing.{JFrame, SwingUtilities, WindowConstants}

class STUI(width: Int, height: Int) extends UI:
  private val frame = JFrame("Game")
  frame.setSize(width, height)
  frame.setVisible(true)
  frame.setLocationRelativeTo(null)
  frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)

  override def events: Observable[Event] = Observable.fromIterable(LazyList.continually(Event.Empty))

  override def render(gw: GameWorld): Task[Unit] = Task {
    SwingUtilities.invokeAndWait { () =>
      println(s"New pos ${gw.player.pos}")
    }
  }
