package org.metricsproc.worker.util

import org.metricsproc.core.util.Config

trait WorkerConfig extends Config {

  val optConfig: OptWorkerConfig

  protected final val KAFKA_SERVER = s"$getConfigPrefix.kafka-server"
  protected final val KAFKA_TOPIC = s"$getConfigPrefix.kafka-topic"
  protected final val KAFKA_CONSUMER_GROUP = s"$getConfigPrefix.kafka-consumer-group"
  protected final val CONSUME_FROM_BEGINNING = s"$getConfigPrefix.consume-from-beginning"
  protected final val SCHEMA_REGISTRY_URL = s"$getConfigPrefix.schema-registry-url"

  def getKafkaServer: String = optConfig.kafkaServer.map(Some(_)).getOrElse(getString(KAFKA_SERVER)).getOrElse("localhost:9092")

  def getKafkaTopic: String = optConfig.kafkaTopic.map(Some(_)).getOrElse(getString(KAFKA_TOPIC)).getOrElse("metrics")

  def getKafkaConsumerGroup: String = optConfig.kafkaConsumerGroup.map(Some(_)).getOrElse(getString(KAFKA_CONSUMER_GROUP)).getOrElse("cassandra-worker")

  def getConsumeFromBeginning: Boolean = optConfig.fromBeginning.map(Some(_)).getOrElse(getBoolean(CONSUME_FROM_BEGINNING)).getOrElse(false)

  def getSchemaRegistryUrl: String = optConfig.kafkaServer.map(Some(_)).getOrElse(getString(SCHEMA_REGISTRY_URL)).getOrElse("http://localhost:8081")

  override def toString: String = {
    s"""$KAFKA_SERVER: $getKafkaServer
$KAFKA_TOPIC: $getKafkaTopic
$KAFKA_CONSUMER_GROUP: $getKafkaConsumerGroup
$CONSUME_FROM_BEGINNING: $getConsumeFromBeginning
$SCHEMA_REGISTRY_URL: $getSchemaRegistryUrl"""
  }
}
