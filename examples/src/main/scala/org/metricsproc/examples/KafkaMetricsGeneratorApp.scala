package org.metricsproc.examples

import kamon.Kamon
import org.metricsproc.core.generator.MetricsGenerator
import org.metricsproc.kafka.writer.KafkaMetricsWriter

object KafkaMetricsGeneratorApp extends App {

  Kamon.init()

  object KMGApp extends KafkaMetricsWriter with MetricsGenerator {
    override def getConfigPrefix: String = "metricsproc.kafka-generator-app"
  }

  KMGApp.generateFixedAmount()
}
