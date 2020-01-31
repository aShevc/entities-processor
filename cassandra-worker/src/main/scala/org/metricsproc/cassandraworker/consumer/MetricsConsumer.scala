package org.metricsproc.cassandraworker.consumer

import scala.util.Try

trait MetricsConsumer {
  def consume(): Try[Unit]
}
