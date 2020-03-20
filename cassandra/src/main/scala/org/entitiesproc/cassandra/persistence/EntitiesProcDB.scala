package org.entitiesproc.cassandra.persistence

import com.outworkers.phantom.dsl._
import org.entitiesproc.cassandra.persistence.entities.Entities
import org.entitiesproc.cassandra.util.CassandraWriterConfig

case class EntitiesProcDB(
                     override val connector: CassandraConnection
                   ) extends Database[EntitiesProcDB](connector) {

  object entities$ extends Entities with Connector
}

trait EntitiesProcDBProvider extends DatabaseProvider[EntitiesProcDB] with CassandraWriterConfig {

  def closeConnection(): Unit = {
    database.connector.provider.cluster.close()
  }
}