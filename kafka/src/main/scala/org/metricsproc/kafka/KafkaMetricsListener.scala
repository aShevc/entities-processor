package org.metricsproc.kafka

import org.metricsproc.core.writer.LogWriter
import org.metricsproc.kafka.listener.KafkaMetricsListener
import org.slf4j.LoggerFactory

object KafkaMetricsListener extends App {

  private val log = LoggerFactory.getLogger(classOf[KafkaMetricsListener])

  object KafkaMetricsListenerApp extends KafkaMetricsListener with LogWriter {
    override def getConfigPrefix: String = "metricsproc.kafka-listener-app"
  }

  KafkaMetricsListenerApp.listen().recover {
    case exc =>
      log.error(s"An unrecoverable error occurred during record processing", exc)
  }
}

