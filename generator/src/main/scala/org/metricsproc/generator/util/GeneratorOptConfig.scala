package org.metricsproc.generator.util

import scopt.{OParser, OParserBuilder}

case class GeneratorOptConfig(duration: Option[Int] = None,
                              sampleRate: Option[Int] = None,
                              kafkaServer: Option[String] = None,
                              kafkaTopic: Option[String] = None,
                              schemaRegistryUrl: Option[String] = None)

object GeneratorOptConfig {

  val builder: OParserBuilder[GeneratorOptConfig] = OParser.builder[GeneratorOptConfig]

  val parser: OParser[Unit, GeneratorOptConfig] = {
    import builder._
    OParser.sequence(
      programName("metrics-generator"),
      head("metrics-generator", "0.1"),
      opt[Int]('d', "duration")
        .action((x, c) => c.copy(duration = Some(x)))
        .text("duration of load in sec"),
      opt[Int]('s', "sampleRate")
        .action((x, c) => c.copy(sampleRate = Some(x)))
        .text("requests per sec"),
      opt[String]('k', "kafkaServer")
        .action((x, c) => c.copy(kafkaServer = Some(x)))
        .text("Kafka bootstrap server"),
      opt[String]('t', "kafkaTopic")
        .action((x, c) => c.copy(kafkaTopic = Some(x)))
        .text("Kafka topic"),
      opt[String]('r', "schemaRegistryUrl")
        .action((x, c) => c.copy(schemaRegistryUrl = Some(x)))
        .text("Schema registry URL")
    )
  }
}
