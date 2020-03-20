package org.entitiesproc.core.generator

import org.entitiesproc.core.util.{AFAP, GeneratorConfig, WithRate}
import org.entitiesproc.core.writer.EntitiesWriter
import org.entitiesproc.entity.Entity
import org.slf4j.LoggerFactory

import scala.util.Random

trait EntitiesGenerator extends GeneratorConfig {
  this: EntitiesWriter =>

  private val log = LoggerFactory.getLogger(classOf[EntitiesGenerator])

  val rnd = new Random()

  def generate(): Unit = {
    getGeneratorMode match {
      case AFAP => generateAFAP()
      case WithRate => generateWithSampleRate()
    }
  }

  private def generateWithSampleRate(): Unit = {

    log.info(s"Started generating entities with config\n$getGeneratorConfig")

    for (_ <- 0 until getDuration) {
      val time = System.currentTimeMillis()
      for (i <- 0 until getSampleRate) {
        write(new Entity(s"device_$i",
          rnd.between(1, 10), System.currentTimeMillis()))
      }
      Thread.sleep(System.currentTimeMillis() - time + 1000)
    }

    closeWriter()
  }

  private def generateAFAP(): Unit = {

    log.info(s"Started generating entities with config\n$getGeneratorConfig")

    var counter = 0

    var time = System.currentTimeMillis()

    for (i <- 0 until getEntitiesAmount) {
      write(new Entity(s"device_$counter",
        rnd.between(1, 10), time))
      counter += 1
      if (counter > getDevicesAmount) {
        counter = 0
        // assuming devices send entities once every second
        time = time + 1000
      }
    }

    closeWriter()
  }
}
