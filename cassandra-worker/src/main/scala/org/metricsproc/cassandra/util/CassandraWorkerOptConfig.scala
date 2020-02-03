package org.metricsproc.cassandra.util

import org.metricsproc.worker.util.OptWorkerConfig
import scopt.{OParser, OParserBuilder}

case class CassandraWorkerOptConfig(override val kafkaServer: Option[String] = None,
                                    override val kafkaTopic: Option[String] = None,
                                    override val kafkaConsumerGroup: Option[String] = None,
                                    override val fromBeginning: Option[Boolean] = None,
                                    override val schemaRegistryUrl: Option[String] = None,
                                    cassandraHost: Option[String] = None,
                                    cassandraPort: Option[Int] = None,
                                    cassandraKeyspace: Option[String] = None) extends OptWorkerConfig {

}

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
        .text("Schema registry URL"),
      opt[String]("cassandraHost")
        .action((x, c) => c.copy(cassandraHost = Some(x)))
        .text("Cassandra contact point host"),
      opt[Int]("cassandraPort")
        .action((x, c) => c.copy(cassandraPort = Some(x)))
        .text("Cassandra contact point port"),
      opt[String]("cassandraKeyspace")
        .action((x, c) => c.copy(cassandraKeyspace = Some(x)))
        .text("Cassandra keyspace")
    )
  }
}



