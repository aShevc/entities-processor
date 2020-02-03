package org.metricsproc.cassandra.worker

import org.metricsproc.cassandra.persistence.metrics.{MetricDTO, MetricsService}
import org.metricsproc.cassandra.util.CassandraWorkerConfig
import org.metricsproc.metric.Metric
import org.metricsproc.worker.MetricsWorker
import org.slf4j.LoggerFactory

trait CassandraMetricsWorker extends MetricsWorker {
  this: MetricsService =>

  private val log = LoggerFactory.getLogger(this.getClass)

  val config: CassandraWorkerConfig
  var counter = 0

  override def processMetrics(metrics: List[Metric]): Unit = {
    metrics.foreach { metric =>
      saveMetric(MetricDTO(metric))
      counter += 1
      if (counter % 100 == 0) {
        log.info(s"Consumed $counter messages.\n The last message consumed: $metric")
      }

      log.debug(s"Consumed a message $metric")
    }
  }
}
