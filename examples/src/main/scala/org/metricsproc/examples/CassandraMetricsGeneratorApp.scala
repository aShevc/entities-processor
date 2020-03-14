package org.metricsproc.examples

import org.metricsproc.cassandra.persistence.DefaultMetricsProcDBProvider
import org.metricsproc.cassandra.persistence.metrics.MetricsService
import org.metricsproc.cassandra.writer.CassandraWriter
import org.metricsproc.core.generator.MetricsGenerator

object CassandraMetricsGeneratorApp extends App {

  object CMGApp extends CassandraWriter with MetricsGenerator with MetricsService with DefaultMetricsProcDBProvider {
    override def getConfigPrefix: String = "metricsproc.cassandra-generator-app"
  }

  CMGApp.generateFixedAmount()
}
