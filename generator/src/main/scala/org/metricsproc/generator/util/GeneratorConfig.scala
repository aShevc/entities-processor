package org.metricsproc.generator.util

import org.metricsproc.core.util.Config

case class GeneratorConfig(optConfig: GeneratorOptConfig) extends Config {
  protected final val SAMPLE_RATE = "metricsproc.generator.sample-rate"
  protected final val DURATION = "metricsproc.generator.duration"
  protected final val KAFKA_SERVER = "metricsproc.generator.kafka-server"

  def getSampleRate: Int = optConfig.sampleRate.map(Some(_)).getOrElse(getInt(SAMPLE_RATE)).getOrElse(100)
  def getDuration: Int = optConfig.duration.map(Some(_)).getOrElse(getInt(DURATION)).getOrElse(10)
  def getKafkaServer: String = optConfig.kafkaServer.map(Some(_)).getOrElse(getString(KAFKA_SERVER)).getOrElse("localhost:9092")

  override def toString: String = {
    s"""$SAMPLE_RATE: $getSampleRate
$DURATION: $getDuration
$KAFKA_SERVER: $getKafkaServer"""
  }
}

