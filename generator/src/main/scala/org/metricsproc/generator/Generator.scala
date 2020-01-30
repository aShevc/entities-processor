package org.metricsproc.generator

import org.metricsproc.generator.generator.KafkaMetricsGenerator
import org.metricsproc.generator.util.{GeneratorConfig, GeneratorOptConfig}
import org.slf4j.LoggerFactory
import scopt.OParser

object Generator extends App {

  private val log = LoggerFactory.getLogger(Generator.getClass)

  OParser.parse(GeneratorOptConfig.parser, args, GeneratorOptConfig()).map(
    x => KafkaMetricsGenerator(GeneratorConfig(x)).generate()).getOrElse {
    log.error("Invalid args")
    System.exit(1)
  }
}

