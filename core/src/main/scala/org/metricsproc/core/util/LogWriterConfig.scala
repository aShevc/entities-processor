package org.metricsproc.core.util

trait LogWriterConfig extends Config {

  private final val LOG_WRITER_PREFIX = "log.writer"

  protected final val LOG_RATE = s"$getConfigPrefix.$LOG_WRITER_PREFIX.log-rate"

  def getLogWriterLogRate: Int = getInt(LOG_RATE).getOrElse(100000)

  def getLogWriterConfig: String = {
    s"""$LOG_RATE: $getLogWriterLogRate """
  }
}
