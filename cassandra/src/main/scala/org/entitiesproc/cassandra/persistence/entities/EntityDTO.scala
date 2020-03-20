package org.entitiesproc.cassandra.persistence.entities

import org.entitiesproc.entity.Entity
import org.joda.time.DateTime

case class EntityDTO(deviceId: String, date: String, time: DateTime, temperature: Int)

object EntityDTO {

  def apply(entity: Entity): EntityDTO = {
    val datetime = new DateTime(entity.getTimestamp)
    val dateFormatted = s"${datetime.getYear}-${datetime.getMonthOfYear}-${datetime.getDayOfMonth}"
    new EntityDTO(entity.getDeviceId.toString, dateFormatted, datetime, entity.getTemperature)
  }
}