package org.metricsproc.kafka.util

import org.metricsproc.core.util.Config

trait KafkaWriterConfig extends Config {

  private final val KAFKA_WRITER_PREFIX = "kafka.writer"

  protected final val KAFKA_WRITER_SERVER = s"$getConfigPrefix.$KAFKA_WRITER_PREFIX.server"
  protected final val KAFKA_WRITER_TOPIC = s"$getConfigPrefix.$KAFKA_WRITER_PREFIX.topic"
  protected final val WRITER_SCHEMA_REGISTRY_URL = s"$getConfigPrefix.$KAFKA_WRITER_PREFIX.schema-registry-url"

  def getKafkaWriterServer: String = getString(KAFKA_WRITER_SERVER).getOrElse("localhost:9092")
  def getKafkaWriterTopic: String = getString(KAFKA_WRITER_TOPIC).getOrElse("metrics")
  def getWriterSchemaRegistryUrl: String = getString(WRITER_SCHEMA_REGISTRY_URL).getOrElse("localhost:8081")

  def getKafkaWriterConfig: String = {
    s"""$KAFKA_WRITER_SERVER: $getKafkaWriterServer
    $KAFKA_WRITER_TOPIC: $getKafkaWriterTopic
    $WRITER_SCHEMA_REGISTRY_URL: $getWriterSchemaRegistryUrl"""
  }
}
