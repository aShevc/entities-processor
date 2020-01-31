package org.metricsproc.cassandraworker

import org.metricsproc.cassandraworker.consumer.KafkaMetricsConsumer
import org.metricsproc.cassandraworker.util.{CassandraWorkerConfig, CassandraWorkerOptConfig}
import org.slf4j.LoggerFactory
import scopt.OParser

object Worker extends App {

  private val log = LoggerFactory.getLogger(Worker.getClass)

  OParser.parse(CassandraWorkerOptConfig.parser, args, CassandraWorkerOptConfig()).map {
    optConfig =>
      KafkaMetricsConsumer(CassandraWorkerConfig(optConfig)).consume().recover {
        case exc =>
          log.error(s"An unrecoverable error occurred during record processing", exc)
      }
  }.getOrElse {
    log.error("Invalid args")
    System.exit(1)
  }
}
