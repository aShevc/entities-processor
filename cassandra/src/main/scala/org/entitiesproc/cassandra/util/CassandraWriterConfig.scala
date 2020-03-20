package org.entitiesproc.cassandra.util

import org.entitiesproc.core.util.Config

trait CassandraWriterConfig extends Config {

  private final val CASSANDRA_WRITER_PREFIX = "cassandra.writer"

  protected final val CASSANDRA_WRITER_HOST = s"$getConfigPrefix.$CASSANDRA_WRITER_PREFIX.host"
  protected final val CASSANDRA_WRITER_PORT = s"$getConfigPrefix.$CASSANDRA_WRITER_PREFIX.port"
  protected final val CASSANDRA_WRITER_KEYSPACE = s"$getConfigPrefix.$CASSANDRA_WRITER_PREFIX.keyspace"
  protected final val CASSANDRA_WRITER_CONCURRENT_CONNECTIONS = s"$getConfigPrefix.$CASSANDRA_WRITER_PREFIX.concurrent-connections"

  def getCassandraWriterHost: String = getString(CASSANDRA_WRITER_HOST).getOrElse("localhost")
  def getCassandraWriterPort: Int = getInt(CASSANDRA_WRITER_PORT).getOrElse(9042)
  def getCassandraWriterKeyspace: String = getString(CASSANDRA_WRITER_KEYSPACE).getOrElse("entities_processor")
  def getCassandraWriterConcurrentConnections: Int = getInt(CASSANDRA_WRITER_CONCURRENT_CONNECTIONS).getOrElse(1000)

  def getCassandraWriterConfig: String = {
    s"""|$CASSANDRA_WRITER_HOST: $getCassandraWriterHost
       |$CASSANDRA_WRITER_PORT: $getCassandraWriterPort
       |$CASSANDRA_WRITER_KEYSPACE: $getCassandraWriterKeyspace
       |$CASSANDRA_WRITER_CONCURRENT_CONNECTIONS: $getCassandraWriterConcurrentConnections
       |""".stripMargin
  }
}

