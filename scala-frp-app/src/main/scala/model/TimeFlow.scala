package model

import monix.reactive.Observable
import scala.concurrent.duration.FiniteDuration

/** Describes the time flow */
object TimeFlow:
  /** Emits events periodically 
   * @param duration period */
  def tickContinually(duration: FiniteDuration): Observable[Long] =
    Observable
      .fromIterable(LazyList.continually(duration))
      .delayOnNext(duration)
      .map(_.toMillis)