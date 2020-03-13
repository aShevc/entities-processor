package org.metricsproc.core.generator

import org.metricsproc.core.util.GeneratorConfig
import org.metricsproc.core.writer.MetricsWriter
import org.metricsproc.metric.Metric
import org.slf4j.LoggerFactory

import scala.util.Random

trait MetricsGenerator extends GeneratorConfig {
  this: MetricsWriter =>

  private val log = LoggerFactory.getLogger(classOf[MetricsGenerator])

  val rnd = new Random()

  def generate() {

    log.info(s"Started generating metrics with config $getGeneratorConfig")

    for (sec <- 0 until getDuration) {
      val time = System.currentTimeMillis()
      for (i <- 0 until getSampleRate) {
        Thread.sleep(2)
        write(new Metric(s"device_$i",
          rnd.between(1, 10), System.currentTimeMillis()))
      }
      Thread.sleep(System.currentTimeMillis() - time + 1000)
    }

    closeWriter()
  }
}
