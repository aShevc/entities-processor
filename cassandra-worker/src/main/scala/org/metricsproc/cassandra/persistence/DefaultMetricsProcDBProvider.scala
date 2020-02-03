package org.metricsproc.cassandra.persistence

import com.outworkers.phantom.connectors.{ContactPoint, KeySpace}
import com.outworkers.phantom.dsl._
import org.metricsproc.cassandra.util.CassandraWorkerConfig

trait DefaultMetricsProcDBProvider extends MetricsProcDBProvider {

  val config: CassandraWorkerConfig

  override lazy val database: MetricsProcDB = MetricsProcDB(ContactPoint(config.getCassandraHost, config.getCassandraPort)
    .keySpace(
      KeySpace(config.getCassandraKeyspace).ifNotExists()
        .`with`(replication eqs SimpleStrategy.replication_factor(1))
        .and(durable_writes eqs true)
    ))
}

