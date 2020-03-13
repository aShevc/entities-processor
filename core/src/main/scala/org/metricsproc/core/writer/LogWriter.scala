package org.metricsproc.core.writer
import org.metricsproc.metric.Metric
import org.slf4j.LoggerFactory

trait LogWriter extends MetricsWriter {

  private val log = LoggerFactory.getLogger(classOf[LogWriter])

  var counter = 0

  override def write(metric: Metric): Unit = {
    counter += 1
    if (counter % 100 == 0) {
      log.info(s"Consumed $counter messages.\n The last message consumed: $metric")
    }

    log.debug(s"Consumed a message $metric")
  }
}
