package org.metricsproc.cassandra.writer

import kamon.Kamon
import org.metricsproc.cassandra.persistence.metrics.{MetricDTO, MetricsService}
import org.metricsproc.core.writer.MetricsWriter
import org.metricsproc.metric.Metric
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

trait CassandraWriter extends MetricsWriter {
  this: MetricsService =>

  private val log = LoggerFactory.getLogger(classOf[CassandraWriter])

  // May consider single metrics name for all writes, single name for all listens, and specify write
  // destination with metrics tags
  private lazy val writesCounter = Kamon.counter("cassandra-writes").withoutTags()

  def write(metric: Metric): Unit = {
    val res = saveMetric(MetricDTO(metric))
    res onComplete {
      case Success(_) =>
        writesCounter.increment()
      case Failure(exc) =>
        log.error("Could not write a metric to Cassandra", exc)
    }
  }

  override def closeWriter(): Unit = {
    closeConnection()
  }
}
