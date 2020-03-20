package org.entitiesproc.app

import org.entitiesproc.core.util.KamonApp
import org.entitiesproc.core.writer.LogWriter
import org.entitiesproc.kafka.listener.KafkaEntitiesListener
import org.slf4j.LoggerFactory

object KafkaEntitiesListenerApp extends App {

  private val log = LoggerFactory.getLogger(this.getClass)

  object KMLApp extends KamonApp with KafkaEntitiesListener with LogWriter {
    override def getCustomConfigFile: Option[String] = Some("kafka-listener-app")
  }

  KMLApp.listen().recover {
    case exc =>
      log.error(s"An unrecoverable error occurred during record processing", exc)
  }
}
