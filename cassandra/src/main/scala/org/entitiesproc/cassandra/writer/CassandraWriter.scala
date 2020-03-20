package org.entitiesproc.cassandra.writer

import java.util.concurrent.Semaphore

import kamon.Kamon
import org.entitiesproc.cassandra.persistence.entities.{EntitiesService, EntityDTO}
import org.entitiesproc.core.writer.EntitiesWriter
import org.entitiesproc.entity.Entity
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success, Try}

trait CassandraWriter extends EntitiesWriter {
  this: EntitiesService =>

  private val log = LoggerFactory.getLogger(classOf[CassandraWriter])

  private lazy val writesCounter = Kamon.counter("cassandra-writes").withoutTags()

  private lazy val semaphore = new Semaphore(getCassandraWriterConcurrentConnections);

  def write(entity: Entity): Unit = {
    semaphore.acquire()
    Try {
      val res = saveEntity(EntityDTO(entity))
      res onComplete {
        case Success(_) =>
          writesCounter.increment()
          semaphore.release()
        case Failure(exc) =>
          log.error("Could not write an entity to Cassandra", exc)
          semaphore.release()
      }
    } recover {
      case t: Throwable =>
        semaphore.release()
        throw t
    }
  }

  override def closeWriter(): Unit = {
    closeConnection()
  }
}
