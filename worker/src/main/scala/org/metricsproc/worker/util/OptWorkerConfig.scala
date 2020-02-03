package org.metricsproc.worker.util

abstract class OptWorkerConfig(val kafkaServer: Option[String] = None,
                               val kafkaTopic: Option[String] = None,
                               val kafkaConsumerGroup: Option[String] = None,
                               val fromBeginning: Option[Boolean] = None,
                               val schemaRegistryUrl: Option[String] = None) {}
