package org.metricsproc.kafka.writer

import java.util.Properties

import io.confluent.kafka.serializers.KafkaAvroSerializer
import org.apache.kafka.clients.producer.{Callback, KafkaProducer, ProducerRecord, RecordMetadata}
import org.metricsproc.core.writer.MetricsWriter
import org.metricsproc.kafka.util.KafkaWriterConfig
import org.metricsproc.metric.Metric
import org.slf4j.LoggerFactory

trait KafkaMetricsWriter extends MetricsWriter with KafkaWriterConfig {

  private val log = LoggerFactory.getLogger(classOf[KafkaMetricsWriter])

  val props = new Properties()

  props.put("bootstrap.servers", getKafkaWriterServer)
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", classOf[KafkaAvroSerializer])
  props.put("schema.registry.url", getWriterSchemaRegistryUrl)

  val callback: Callback = (_: RecordMetadata, exception: Exception) => {
    if (exception != null) {
      log.warn(s"could not produce a record: $exception")
    }
  }

  val producer = new KafkaProducer[String, Metric](props)

  log.info(s"Starting Kafka metrics writer with config:\n$getKafkaWriterConfig")

  override def write(metric: Metric): Unit = {
    producer.send(new ProducerRecord(getKafkaWriterTopic, metric.getDeviceId.toString, metric), callback)
  }

  override def closeWriter(): Unit = {
    producer.close()
  }
}
