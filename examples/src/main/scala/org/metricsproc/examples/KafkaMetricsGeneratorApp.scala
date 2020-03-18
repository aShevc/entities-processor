package org.metricsproc.examples

import com.typesafe.config.ConfigFactory
import kamon.Kamon
import org.metricsproc.core.generator.MetricsGenerator
import org.metricsproc.kafka.writer.KafkaMetricsWriter

object KafkaMetricsGeneratorApp extends App {

  val customConfig = ConfigFactory.load("application.conf")
  val codeConfig = ConfigFactory.parseString("kamon.prometheus.embedded-server.port = 9096")

  Kamon.reconfigure(codeConfig.withFallback(customConfig))

  Kamon.init()

  object KMGApp extends KafkaMetricsWriter with MetricsGenerator {
    override def getConfigPrefix: String = "metricsproc.kafka-generator-app"
  }

  KMGApp.generate()

  System.exit(0)
}
