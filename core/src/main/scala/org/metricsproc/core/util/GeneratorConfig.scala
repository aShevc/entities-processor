package org.metricsproc.core.util

trait GeneratorConfig extends Config {

  protected final val SAMPLE_RATE = s"$getConfigPrefix.sample-rate"
  protected final val DURATION = s"$getConfigPrefix.duration"
  protected final val METRICS_AMOUNT = s"$getConfigPrefix.metrics-amount"
  protected final val DEVICES_AMOUNT = s"$getConfigPrefix.devices-amount"

  def getSampleRate: Int = getInt(SAMPLE_RATE).getOrElse(100)
  def getDuration: Int = getInt(DURATION).getOrElse(60)
  def getMetricsAmount: Int = getInt(METRICS_AMOUNT).getOrElse(100000)
  def getDevicesAmount: Int = getInt(DEVICES_AMOUNT).getOrElse(10000)

  def getGeneratorConfig: String = {
    s"""$SAMPLE_RATE: $getSampleRate
$DURATION: $getDuration
$METRICS_AMOUNT: $getMetricsAmount
$DEVICES_AMOUNT: $getDevicesAmount """
  }
}
