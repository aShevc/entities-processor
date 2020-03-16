package org.metricsproc.examples

import kamon.Kamon
import org.metricsproc.core.generator.MetricsGenerator
import org.metricsproc.core.writer.LogWriter

object LogMetricsGeneratorApp extends App {

  Kamon.init()

  object LMGApp extends LogWriter with MetricsGenerator {
    override def getConfigPrefix: String = "metricsproc.log-generator-app"
  }

  LMGApp.generateFixedAmount()
}
