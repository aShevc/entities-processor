package org.metricsproc.cassandra.writer

import org.metricsproc.cassandra.persistence.metrics.{MetricDTO, MetricsService}
import org.metricsproc.core.writer.MetricsWriter
import org.metricsproc.metric.Metric

trait CassandraWriter extends MetricsWriter {
  this: MetricsService =>

  def write(metric: Metric): Unit = {
    saveMetric(MetricDTO(metric))
  }
}
