package org.metricsproc.cassandra.persistence

import com.outworkers.phantom.connectors.{ContactPoint, KeySpace}
import com.outworkers.phantom.dsl._
import org.metricsproc.cassandra.util.CassandraWriterConfig
import org.slf4j.LoggerFactory

trait DefaultMetricsProcDBProvider extends MetricsProcDBProvider with CassandraWriterConfig {

  private val log = LoggerFactory.getLogger(this.getClass)

  log.info(s"Initiating Cassandra DB connection with config\n$getCassandraWriterConfig")

  override lazy val database: MetricsProcDB = MetricsProcDB(ContactPoint(getCassandraWriterHost, getCassandraWriterPort)
    .keySpace(
      KeySpace(getCassandraWriterKeyspace).ifNotExists()
        .`with`(replication eqs SimpleStrategy.replication_factor(1))
        .and(durable_writes eqs true)
    ))
}

