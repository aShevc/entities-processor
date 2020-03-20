package org.entitiesproc.app

import org.entitiesproc.core.generator.EntitiesGenerator
import org.entitiesproc.core.util.KamonApp
import org.entitiesproc.core.writer.LogWriter

object LogEntitiesGeneratorApp extends App {

  object LMGApp extends KamonApp with LogWriter with EntitiesGenerator {
    override def getCustomConfigFile: Option[String] = Some("log-generator-app")
  }

  LMGApp.generate()
}
