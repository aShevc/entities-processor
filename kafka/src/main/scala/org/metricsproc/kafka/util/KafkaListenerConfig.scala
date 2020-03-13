package org.metricsproc.kafka.util

import org.metricsproc.core.util.Config

trait KafkaListenerConfig extends Config {

  private final val KAFKA_LISTENER_PREFIX = "kafka.listener"

  protected final val KAFKA_LISTENER_SERVER = s"$getConfigPrefix.$KAFKA_LISTENER_PREFIX.server"
  protected final val KAFKA_LISTENER_TOPIC = s"$getConfigPrefix$KAFKA_LISTENER_PREFIX.topic"
  protected final val KAFKA_CONSUMER_GROUP = s"$getConfigPrefix.$KAFKA_LISTENER_PREFIX.consumer-group"
  protected final val CONSUME_FROM_BEGINNING = s"$getConfigPrefix.$KAFKA_LISTENER_PREFIX.consume-from-beginning"
  protected final val KAFKA_LISTENER_SCHEMA_REGISTRY_URL = s"$getConfigPrefix.$KAFKA_LISTENER_PREFIX.schema-registry-url"

  def getKafkaListenerServer: String = getString(KAFKA_LISTENER_SERVER).getOrElse("localhost:9092")

  def getKafkaListenerTopic: String = getString(KAFKA_LISTENER_TOPIC).getOrElse("metrics")

  def getKafkaConsumerGroup: String = getString(KAFKA_CONSUMER_GROUP).getOrElse("cassandra-worker")

  def getConsumeFromBeginning: Boolean = getBoolean(CONSUME_FROM_BEGINNING).getOrElse(false)

  def getListenerSchemaRegistryUrl: String = getString(KAFKA_LISTENER_SCHEMA_REGISTRY_URL).getOrElse("http://localhost:8081")

  def getKafkaListenerConfig: String = {
    s"""$KAFKA_LISTENER_SERVER: $getKafkaListenerServer
$KAFKA_LISTENER_TOPIC: $getKafkaListenerTopic
$KAFKA_CONSUMER_GROUP: $getKafkaConsumerGroup
$CONSUME_FROM_BEGINNING: $getConsumeFromBeginning
$KAFKA_LISTENER_SCHEMA_REGISTRY_URL: $getListenerSchemaRegistryUrl"""
  }
}
