package org.metricsproc.worker

import org.metricsproc.metric.Metric

trait MetricsWorker {

  def processMetrics(metrics: List[Metric])
}
