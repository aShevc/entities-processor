package org.metricsproc.cassandra

import org.metricsproc.cassandra.persistence.DefaultMetricsProcDBProvider
import org.metricsproc.cassandra.persistence.metrics.MetricsService
import org.metricsproc.cassandra.worker.CassandraWriter
import org.metricsproc.core.generator.MetricsGenerator

object CassandraMetricsGenerator extends App {

  object KafkaMetricsGeneratorApp extends CassandraWriter with MetricsGenerator with MetricsService with DefaultMetricsProcDBProvider {
    override def getConfigPrefix: String = "metricsproc.kafka-generator-app"
  }

  KafkaMetricsGeneratorApp.generate()
}
