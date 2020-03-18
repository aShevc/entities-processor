package org.metricsproc.core.util

import org.slf4j.LoggerFactory

trait GeneratorConfig extends Config {

  protected final val SAMPLE_RATE = s"$getConfigPrefix.sample-rate"
  protected final val DURATION = s"$getConfigPrefix.duration"
  protected final val METRICS_AMOUNT = s"$getConfigPrefix.metrics-amount"
  protected final val DEVICES_AMOUNT = s"$getConfigPrefix.devices-amount"
  protected final val GENERATOR_MODE = s"$getConfigPrefix.generator-mode"

  def getSampleRate: Int = getInt(SAMPLE_RATE).getOrElse(100)

  def getDuration: Int = getInt(DURATION).getOrElse(60)

  def getMetricsAmount: Int = getInt(METRICS_AMOUNT).getOrElse(100000)

  def getDevicesAmount: Int = getInt(DEVICES_AMOUNT).getOrElse(10000)

  def getGeneratorMode: GeneratorMode = getString(GENERATOR_MODE).flatMap(GeneratorMode(_)).getOrElse(AFAP)

  def getGeneratorConfig: String = {
    s"""$SAMPLE_RATE: $getSampleRate
$DURATION: $getDuration
$METRICS_AMOUNT: $getMetricsAmount
$DEVICES_AMOUNT: $getDevicesAmount
$GENERATOR_MODE: ${getGeneratorMode.name} """
  }
}

sealed trait GeneratorMode {
  val name: String
}

case object AFAP extends GeneratorMode {
  override val name: String = "AFAP"
}

case object WithRate extends GeneratorMode {
  override val name: String = "with-rate"
}

object GeneratorMode {

  private val log = LoggerFactory.getLogger(classOf[GeneratorMode])

  def apply(s: String): Option[GeneratorMode] = {
    s match {
      case AFAP.name => Some(AFAP)
      case WithRate.name => Some(WithRate)
      case illegal =>
        log.warn(s"Illegal argument set as the generator mode in config: $illegal")
        None
    }
  }
}