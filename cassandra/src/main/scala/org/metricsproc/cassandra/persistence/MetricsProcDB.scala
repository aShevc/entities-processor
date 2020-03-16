package org.metricsproc.cassandra.persistence

import com.outworkers.phantom.dsl._
import org.metricsproc.cassandra.persistence.metrics.Metrics
import org.metricsproc.cassandra.util.CassandraWriterConfig

case class MetricsProcDB(
                     override val connector: CassandraConnection
                   ) extends Database[MetricsProcDB](connector) {

  object metrics extends Metrics with Connector
}

trait MetricsProcDBProvider extends DatabaseProvider[MetricsProcDB] with CassandraWriterConfig {

  def closeConnection(): Unit = {
    database.connector.provider.cluster.close()
  }
}