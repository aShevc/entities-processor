package org.metricsproc.cassandra.persistence.metrics

import org.joda.time.DateTime
import org.metricsproc.metric.Metric

case class MetricDTO(deviceId: String, date: String, time: DateTime, temperature: Int)

object MetricDTO {

  def apply(metric: Metric): MetricDTO = {
    val datetime = new DateTime(metric.getTimestamp)
    val dateFormatted = s"${datetime.getYear}-${datetime.getMonthOfYear}-${datetime.getDayOfMonth}"
    new MetricDTO(metric.getDeviceId.toString, dateFormatted, datetime, metric.getTemperature)
  }
}