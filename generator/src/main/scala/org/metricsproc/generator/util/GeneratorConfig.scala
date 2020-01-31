package org.metricsproc.generator.util

import org.metricsproc.core.util.Config

case class GeneratorConfig(optConfig: GeneratorOptConfig) extends Config {
  private final val CONFIG_PREFIX = "metricsproc.generator"

  protected final val SAMPLE_RATE = s"$CONFIG_PREFIX.sample-rate"
  protected final val DURATION = s"$CONFIG_PREFIX.duration"
  protected final val KAFKA_SERVER = s"$CONFIG_PREFIX.kafka-server"
  protected final val KAFKA_TOPIC = s"$CONFIG_PREFIX.kafka-topic"
  protected final val SCHEMA_REGISTRY_URL = s"$CONFIG_PREFIX.schema-registry-url"

  def getSampleRate: Int = optConfig.sampleRate.map(Some(_)).getOrElse(getInt(SAMPLE_RATE)).getOrElse(100)
  def getDuration: Int = optConfig.duration.map(Some(_)).getOrElse(getInt(DURATION)).getOrElse(10)
  def getKafkaServer: String = optConfig.kafkaServer.map(Some(_)).getOrElse(getString(KAFKA_SERVER)).getOrElse("localhost:9092")
  def getKafkaTopic: String = optConfig.kafkaTopic.map(Some(_)).getOrElse(getString(KAFKA_TOPIC)).getOrElse("metrics")
  def getSchemaRegistryUrl: String = optConfig.schemaRegistryUrl.map(Some(_)).getOrElse(getString(SCHEMA_REGISTRY_URL)).getOrElse("localhost:8081")

  override def toString: String = {
    s"""$SAMPLE_RATE: $getSampleRate
$DURATION: $getDuration
$KAFKA_SERVER: $getKafkaServer
$KAFKA_TOPIC: $getKafkaTopic
$SCHEMA_REGISTRY_URL: $getSchemaRegistryUrl"""
  }
}

