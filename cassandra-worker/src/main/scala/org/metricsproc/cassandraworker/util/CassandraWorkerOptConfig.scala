package org.metricsproc.cassandraworker.util

import scopt.{OParser, OParserBuilder}

case class CassandraWorkerOptConfig(kafkaServer: Option[String] = None,
                                    kafkaTopic: Option[String] = None,
                                    kafkaConsumerGroup: Option[String] = None,
                                    fromBeginning: Option[Boolean] = None,
                                    schemaRegistryUrl: Option[String] = None)

object CassandraWorkerOptConfig {

  val builder: OParserBuilder[CassandraWorkerOptConfig] = OParser.builder[CassandraWorkerOptConfig]

  val parser: OParser[Unit, CassandraWorkerOptConfig] = {
    import builder._
    OParser.sequence(
      programName("metrics-cassandra-worker"),
      opt[String]('k', "kafkaServer")
        .action((x, c) => c.copy(kafkaServer = Some(x)))
        .text("Kafka bootstrap server"),
      opt[String]('t', "kafkaTopic")
        .action((x, c) => c.copy(kafkaTopic = Some(x)))
        .text("Kafka topic"),
      opt[String]('g', "kafkaConsumerGroup")
        .action((x, c) => c.copy(kafkaConsumerGroup = Some(x)))
        .text("Kafka topic"),
      opt[Boolean]("fromBeginning")
        .action((x, c) => c.copy(fromBeginning = Some(x)))
        .text("Whether to consume from beginning"),
      opt[String]('r', "schemaRegistryUrl")
        .action((x, c) => c.copy(schemaRegistryUrl = Some(x)))
        .text("Schema registry URL")
    )
  }
}



