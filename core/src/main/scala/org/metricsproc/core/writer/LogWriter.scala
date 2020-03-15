package org.metricsproc.core.writer
import kamon.Kamon
import org.metricsproc.metric.Metric
import org.slf4j.LoggerFactory

trait LogWriter extends MetricsWriter {

  private val log = LoggerFactory.getLogger(classOf[LogWriter])

  var counter = 0

  private lazy val writesCounter = Kamon.counter("log-writes").withoutTags()

  override def write(metric: Metric): Unit = {
    counter += 1
    writesCounter.increment()
    if (counter % 100 == 0) {
      log.info(s"Consumed $counter messages.\n The last message consumed: $metric")
    }

    log.debug(s"Consumed a message $metric")
  }
}
