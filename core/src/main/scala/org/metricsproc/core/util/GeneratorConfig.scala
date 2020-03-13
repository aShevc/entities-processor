package org.metricsproc.core.util

trait GeneratorConfig extends Config {

  protected final val SAMPLE_RATE = s"$getConfigPrefix.sample-rate"
  protected final val DURATION = s"$getConfigPrefix.duration"

  def getSampleRate: Int = getInt(SAMPLE_RATE).getOrElse(100)
  def getDuration: Int = getInt(DURATION).getOrElse(10)

  def getGeneratorConfig: String = {
    s"""$SAMPLE_RATE: $getSampleRate
$DURATION: $getDuration"""
  }
}
