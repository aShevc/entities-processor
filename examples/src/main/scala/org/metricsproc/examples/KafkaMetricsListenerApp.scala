package org.metricsproc.examples

import com.typesafe.config.ConfigFactory
import kamon.Kamon
import org.metricsproc.core.writer.LogWriter
import org.metricsproc.kafka.listener.KafkaMetricsListener
import org.slf4j.LoggerFactory

object KafkaMetricsListenerApp extends App {

  val customConfig = ConfigFactory.load("application.conf")
  val codeConfig = ConfigFactory.parseString("kamon.prometheus.embedded-server.port = 9097")

  Kamon.reconfigure(codeConfig.withFallback(customConfig))

  Kamon.init()

  private val log = LoggerFactory.getLogger(this.getClass)

  object KMLApp extends KafkaMetricsListener with LogWriter {
    override def getConfigPrefix: String = "metricsproc.kafka-listener-app"
  }

  KMLApp.listen().recover {
    case exc =>
      log.error(s"An unrecoverable error occurred during record processing", exc)
  }
}
