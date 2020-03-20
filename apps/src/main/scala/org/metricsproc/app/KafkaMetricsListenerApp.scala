package org.metricsproc.app

import org.metricsproc.core.util.KamonApp
import org.metricsproc.core.writer.LogWriter
import org.metricsproc.kafka.listener.KafkaMetricsListener
import org.slf4j.LoggerFactory

object KafkaMetricsListenerApp extends App {

  private val log = LoggerFactory.getLogger(this.getClass)

  object KMLApp extends KamonApp with KafkaMetricsListener with LogWriter {
    override def getCustomConfigFile: Option[String] = Some("kafka-listener-app")
  }

  KMLApp.listen().recover {
    case exc =>
      log.error(s"An unrecoverable error occurred during record processing", exc)
  }
}
