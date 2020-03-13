package org.metricsproc.core.writer

import org.metricsproc.metric.Metric

trait MetricsWriter {

  def write(metric: Metric): Unit

  def write(metrics: List[Metric]): Unit = {
    metrics.foreach(write)
  }

  def closeWriter(): Unit = {}
}
