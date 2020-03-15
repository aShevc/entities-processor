package org.metricsproc.examples

import kamon.Kamon
import org.metricsproc.cassandra.persistence.DefaultMetricsProcDBProvider
import org.metricsproc.cassandra.persistence.metrics.MetricsService
import org.metricsproc.cassandra.writer.CassandraWriter
import org.metricsproc.core.generator.MetricsGenerator

object CassandraMetricsGeneratorApp extends App {

  Kamon.init()

  object CMGApp extends CassandraWriter with MetricsGenerator with MetricsService with DefaultMetricsProcDBProvider {
    override def getConfigPrefix: String = "metricsproc.cassandra-generator-app"
  }

  CMGApp.generateFixedAmount()

  // Somehow although Cassandra connection is being closed, the app keeps running due to some related threads running.
  // Adding this for now
  System.exit(0)
}
