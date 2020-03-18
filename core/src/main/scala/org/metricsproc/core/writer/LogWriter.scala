package org.metricsproc.core.writer
import kamon.Kamon
import org.metricsproc.core.util.LogWriterConfig
import org.metricsproc.metric.Metric
import org.slf4j.LoggerFactory

trait LogWriter extends MetricsWriter with LogWriterConfig {

  private val log = LoggerFactory.getLogger(classOf[LogWriter])

  var counter = 0

  private lazy val writesCounter = Kamon.counter("log-writes").withoutTags()

  log.info(s"Starting Log writer with config:\n$getLogWriterConfig")

  override def write(metric: Metric): Unit = {
    counter += 1
    writesCounter.increment()
    if (counter % getLogWriterLogRate == 0) {
      log.info(s"Consumed $counter messages.\n The last message consumed: $metric")
    }

    log.debug(s"Consumed a message $metric")
  }
}
