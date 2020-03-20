package org.metricsproc.app

import org.metricsproc.cassandra.persistence.DefaultMetricsProcDBProvider
import org.metricsproc.cassandra.persistence.metrics.MetricsService
import org.metricsproc.cassandra.writer.CassandraWriter
import org.metricsproc.core.generator.MetricsGenerator
import org.metricsproc.core.util.KamonApp

object CassandraMetricsGeneratorApp extends App {

  object CMGApp extends KamonApp with CassandraWriter with MetricsGenerator with MetricsService with DefaultMetricsProcDBProvider {
    override def getCustomConfigFile: Option[String] = Some("cassandra-generator-app")
  }

  CMGApp.generate()

  // Somehow although Cassandra connection is being closed, the app keeps running due to some related threads running.
  // Adding this for now
  System.exit(0)
}
