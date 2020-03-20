package org.entitiesproc.cassandra.persistence.entities

import com.outworkers.phantom.{ResultSet, Table}
import org.entitiesproc.cassandra.persistence.EntitiesProcDBProvider

import scala.concurrent.Future
import com.outworkers.phantom.dsl._
import com.outworkers.phantom.keys.PartitionKey

abstract class Entities extends Table[Entities, EntityDTO] {
  override val tableName = "temperature_metrics"

  object deviceId extends StringColumn with PartitionKey {
    override val name: String = "device_id"
  }
  object date extends StringColumn
  object time extends DateTimeColumn
  object temperature extends IntColumn
}

trait EntitiesService extends EntitiesProcDBProvider {
  def saveEntity(entity: EntityDTO): Future[ResultSet] = {
    db.entities$.store(entity).future()
  }
}