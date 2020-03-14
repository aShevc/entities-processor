package org.metricsproc.examples

import org.metricsproc.core.writer.LogWriter
import org.metricsproc.kafka.listener.KafkaMetricsListener
import org.slf4j.LoggerFactory

object KafkaMetricsListenerApp extends App {

  private val log = LoggerFactory.getLogger(this.getClass)

  object KMLApp extends KafkaMetricsListener with LogWriter {
    override def getConfigPrefix: String = "metricsproc.kafka-listener-app"
  }

  KMLApp.listen().recover {
    case exc =>
      log.error(s"An unrecoverable error occurred during record processing", exc)
  }
}
