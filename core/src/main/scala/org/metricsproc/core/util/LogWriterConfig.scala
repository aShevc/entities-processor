package org.metricsproc.core.util

trait LogWriterConfig extends Config {
  protected final val LOG_RATE = s"$getConfigPrefix.log-rate"

  def getLogWriterLogRate: Int = getInt(LOG_RATE).getOrElse(1000)

  def getLogWriterConfig: String = {
    s"""$LOG_RATE: $getLogWriterLogRate """
  }
}
