package org.entitiesproc.core.writer
import kamon.Kamon
import org.entitiesproc.core.util.LogWriterConfig
import org.entitiesproc.entity.Entity
import org.slf4j.LoggerFactory

trait LogWriter extends EntitiesWriter with LogWriterConfig {

  private val log = LoggerFactory.getLogger(classOf[LogWriter])

  var counter = 0

  private lazy val writesCounter = Kamon.counter("log-writes").withoutTags()

  log.info(s"Starting Log writer with config:\n$getLogWriterConfig")

  override def write(entity: Entity): Unit = {
    counter += 1
    writesCounter.increment()
    if (counter % getLogWriterLogRate == 0) {
      log.info(s"Consumed $counter messages.\n The last message consumed: $entity")
    }

    log.debug(s"Consumed a message $entity")
  }
}
