package org.entitiesproc.app

import org.entitiesproc.cassandra.persistence.DefaultEntitiesProcDBProvider
import org.entitiesproc.cassandra.persistence.entities.EntitiesService
import org.entitiesproc.cassandra.writer.CassandraWriter
import org.entitiesproc.core.generator.EntitiesGenerator
import org.entitiesproc.core.util.KamonApp

object CassandraEntitiesGeneratorApp extends App {

  object CMGApp extends KamonApp with CassandraWriter with EntitiesGenerator with EntitiesService with DefaultEntitiesProcDBProvider {
    override def getCustomConfigFile: Option[String] = Some("cassandra-generator-app")
  }

  CMGApp.generate()

  // Somehow although Cassandra connection is being closed, the app keeps running due to some related threads running.
  // Adding this for now
  System.exit(0)
}
