package org.metricsproc.cassandra.persistence.metrics

import com.outworkers.phantom.{ResultSet, Table}
import org.metricsproc.cassandra.persistence.MetricsProcDBProvider

import scala.concurrent.Future
import com.outworkers.phantom.dsl._
import com.outworkers.phantom.keys.PartitionKey

abstract class Metrics extends Table[Metrics, MetricDTO] {
  override val tableName = "metrics_temperature"

  object deviceId extends StringColumn with PartitionKey {
    override val name: String = "device_id"
  }
  object date extends StringColumn
  object time extends DateTimeColumn
  object temperature extends IntColumn
}

trait MetricsService extends MetricsProcDBProvider {
  def saveMetric(metric: MetricDTO): Future[ResultSet] = {
    db.metrics.store(metric).future()
  }
}