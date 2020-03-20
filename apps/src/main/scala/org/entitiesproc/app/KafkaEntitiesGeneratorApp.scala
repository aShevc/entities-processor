package org.entitiesproc.app

import org.entitiesproc.core.generator.EntitiesGenerator
import org.entitiesproc.core.util.KamonApp
import org.entitiesproc.kafka.writer.KafkaEntitiesWriter

object KafkaEntitiesGeneratorApp extends App {

  object KMGApp extends KamonApp with KafkaEntitiesWriter with EntitiesGenerator {
    override def getCustomConfigFile: Option[String] = Some("kafka-generator-app")
  }

  KMGApp.generate()

  System.exit(0)
}
