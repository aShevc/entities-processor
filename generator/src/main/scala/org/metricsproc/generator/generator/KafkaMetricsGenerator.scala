package org.metricsproc.generator.generator

import java.util.Properties

import io.confluent.kafka.serializers.KafkaAvroSerializer
import org.apache.kafka.clients.producer.{Callback, KafkaProducer, ProducerRecord, RecordMetadata}
import org.metricsproc.generator.util.GeneratorConfig
import org.metricsproc.metric.Metric
import org.slf4j.LoggerFactory
import scala.util.Random

case class KafkaMetricsGenerator(config: GeneratorConfig) extends MetricsGenerator {

  private val log = LoggerFactory.getLogger(classOf[KafkaMetricsGenerator])

  override def generate(): Unit = {
    log.info(s"Starting Kafka metrics generator with config:\n$config")

    val props = new Properties()

    props.put("bootstrap.servers", config.getKafkaServer)
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", classOf[KafkaAvroSerializer])
    props.put("schema.registry.url", config.getSchemaRegistryUrl)

    val callback = new Callback {
      override def onCompletion(metadata: RecordMetadata, exception: Exception): Unit = {
        if (exception != null) {
          log.warn(s"could not produce a record: $exception")
        }
      }
    }

    val producer = new KafkaProducer[String, Metric](props)
    val rnd = new Random()

    for (sec <- 0 until config.getDuration) {
      val time = System.currentTimeMillis()
      for (i <- 0 until config.getSampleRate) {
        Thread.sleep(2)
        producer.send(new ProducerRecord(config.getKafkaTopic, s"device_$i", new Metric(s"device_$i",
          rnd.between(1, 10), System.currentTimeMillis())), callback)
      }
      Thread.sleep(System.currentTimeMillis() - time + 1000)
    }

    producer.close()
  }
}
