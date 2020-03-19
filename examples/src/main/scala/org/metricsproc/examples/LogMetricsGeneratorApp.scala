package org.metricsproc.examples

import org.metricsproc.core.generator.MetricsGenerator
import org.metricsproc.core.util.KamonApp
import org.metricsproc.core.writer.LogWriter

object LogMetricsGeneratorApp extends App {

  object LMGApp extends KamonApp with LogWriter with MetricsGenerator {
    override def getCustomConfigFile: Option[String] = Some("log-generator-app")
  }

  LMGApp.generate()
}
