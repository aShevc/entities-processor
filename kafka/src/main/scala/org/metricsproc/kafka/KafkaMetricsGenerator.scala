package org.metricsproc.kafka

import org.metricsproc.core.generator.MetricsGenerator
import org.metricsproc.kafka.writer.KafkaMetricsWriter

object KafkaMetricsGenerator extends App {

  object KafkaMetricsGeneratorApp extends KafkaMetricsWriter with MetricsGenerator {
    override def getConfigPrefix: String = "metricsproc.kafka-generator-app"
  }

  KafkaMetricsGeneratorApp.generate()
}

