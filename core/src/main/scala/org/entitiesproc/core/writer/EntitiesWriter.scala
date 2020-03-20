package org.entitiesproc.core.writer

import org.entitiesproc.entity.Entity

trait EntitiesWriter {

  def write(entity: Entity): Unit

  def write(entities: List[Entity]): Unit = {
    entities.foreach(write)
  }

  def closeWriter(): Unit = {}
}
