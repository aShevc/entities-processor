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

  def generateWithSampleRate() {

    log.info(s"Started generating metrics with config\n$getGeneratorConfig")

    for (_ <- 0 until getDuration) {
      val time = System.currentTimeMillis()
      for (i <- 0 until getSampleRate) {
        write(new Metric(s"device_$i",
          rnd.between(1, 10), System.currentTimeMillis()))
      }
      Thread.sleep(System.currentTimeMillis() - time + 1000)
    }

    closeWriter()
  }

  def generateFixedAmount(): Unit = {

    log.info(s"Started generating metrics with config\n$getGeneratorConfig")

    var counter = 0

    var time = System.currentTimeMillis()

    for (i <- 0 until getMetricsAmount) {
      write(new Metric(s"device_$counter",
        rnd.between(1, 10), time))
      counter += 1
      if (counter > getDevicesAmount) {
        counter = 0
        // assuming devices send metrics once every second
        time = time + 1000
      }
    }
  }
}
