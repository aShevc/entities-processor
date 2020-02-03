package org.metricsproc.cassandra.util

import org.metricsproc.worker.util.WorkerConfig

case class CassandraWorkerConfig(override val optConfig: CassandraWorkerOptConfig) extends WorkerConfig {

  override def getConfigPrefix: String = "metricsproc.cassandra-worker"

  protected final val CASSANDRA_HOST = s"$getConfigPrefix.cassandra.host"
  protected final val CASSANDRA_PORT = s"$getConfigPrefix.cassandra.port"
  protected final val CASSANDRA_KEYSPACE = s"$getConfigPrefix.cassandra.keyspace"

  def getCassandraHost: String = optConfig.cassandraHost.map(Some(_)).getOrElse(getString(CASSANDRA_HOST)).getOrElse("localhost")

  def getCassandraPort: Int = optConfig.cassandraPort.map(Some(_)).getOrElse(getInt(CASSANDRA_PORT)).getOrElse(9042)

  def getCassandraKeyspace: String = optConfig.cassandraKeyspace.map(Some(_)).getOrElse(getString(CASSANDRA_KEYSPACE)).getOrElse("metrics_processor")

  override def toString: String = {
    s"""${super.toString()}
       |$CASSANDRA_HOST: $getCassandraHost
       |$CASSANDRA_PORT: $getCassandraPort
       |$CASSANDRA_KEYSPACE: $getCassandraKeyspace
       |""".stripMargin
  }
}

