package org.metricsproc.cassandra.writer

import java.util.concurrent.Semaphore

import kamon.Kamon
import org.metricsproc.cassandra.persistence.metrics.{MetricDTO, MetricsService}
import org.metricsproc.core.writer.MetricsWriter
import org.metricsproc.metric.Metric
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success, Try}

trait CassandraWriter extends MetricsWriter {
  this: MetricsService =>

  private val log = LoggerFactory.getLogger(classOf[CassandraWriter])

  // May consider single metrics name for all writes, single name for all listens, and specify write
  // destination with metrics tags
  private lazy val writesCounter = Kamon.counter("cassandra-writes").withoutTags()

  private lazy val semaphore = new Semaphore(getCassandraWriterConcurrentConnections);

  def write(metric: Metric): Unit = {
    semaphore.acquire()
    Try {
      val res = saveMetric(MetricDTO(metric))
      res onComplete {
        case Success(_) =>
          writesCounter.increment()
          semaphore.release()
        case Failure(exc) =>
          log.error("Could not write a metric to Cassandra", exc)
          semaphore.release()
      }
    } recover {
      case t: Throwable =>
        semaphore.release()
        throw t
    }
  }

  override def closeWriter(): Unit = {
    closeConnection()
  }
}
