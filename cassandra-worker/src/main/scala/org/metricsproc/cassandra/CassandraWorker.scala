package org.metricsproc.cassandra

import org.metricsproc.cassandra.persistence.DefaultMetricsProcDBProvider
import org.metricsproc.cassandra.persistence.metrics.MetricsService
import org.metricsproc.cassandra.util.{CassandraWorkerConfig, CassandraWorkerOptConfig}
import org.metricsproc.cassandra.worker.CassandraMetricsWorker
import org.metricsproc.worker.consumer.KafkaMetricsConsumer
import org.slf4j.LoggerFactory
import scopt.OParser

object CassandraWorker extends App {

  private val log = LoggerFactory.getLogger(CassandraWorker.getClass)

  OParser.parse(CassandraWorkerOptConfig.parser, args, CassandraWorkerOptConfig()).map {
    optConfig =>
      new KafkaMetricsConsumer with CassandraMetricsWorker with MetricsService with DefaultMetricsProcDBProvider {
        override val config: CassandraWorkerConfig = CassandraWorkerConfig(optConfig)
      }.consume().recover {
        case exc =>
          log.error(s"An unrecoverable error occurred during record processing", exc)
      }
  }.getOrElse {
    log.error("Invalid args")
    System.exit(1)
  }
}
