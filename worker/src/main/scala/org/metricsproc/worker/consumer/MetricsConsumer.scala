package org.metricsproc.worker.consumer

import scala.util.Try

trait MetricsConsumer {

  def consume(): Try[Unit]
}
