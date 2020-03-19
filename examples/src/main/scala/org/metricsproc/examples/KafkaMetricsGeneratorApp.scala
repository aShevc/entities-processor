package org.metricsproc.examples

import org.metricsproc.core.generator.MetricsGenerator
import org.metricsproc.core.util.KamonApp
import org.metricsproc.kafka.writer.KafkaMetricsWriter

object KafkaMetricsGeneratorApp extends App {

  object KMGApp extends KamonApp with KafkaMetricsWriter with MetricsGenerator {
    override def getCustomConfigFile: Option[String] = Some("kafka-generator-app")
  }

  KMGApp.generate()

  System.exit(0)
}
