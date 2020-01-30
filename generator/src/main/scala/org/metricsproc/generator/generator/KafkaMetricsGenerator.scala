package org.metricsproc.generator.generator

import java.util.Properties

import org.apache.kafka.clients.producer.{Callback, KafkaProducer, ProducerRecord, RecordMetadata}
import org.metricsproc.generator.util.GeneratorConfig
import org.slf4j.LoggerFactory

case class KafkaMetricsGenerator(config: GeneratorConfig) extends MetricsGenerator {

  private val log = LoggerFactory.getLogger(classOf[KafkaMetricsGenerator])

  override def generate(): Unit = {
    log.info(s"Starting Kafka metrics generator with config:\n$config")

    val props = new Properties()

    props.put("bootstrap.servers", config.getKafkaServer)
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)

    val callback = new Callback {
      override def onCompletion(metadata: RecordMetadata, exception: Exception): Unit = {
        if (exception != null) {
          log.warn(s"could not produce a record: $exception")
        }
      }
    }

    for (sec <- 0 until config.getDuration) {
      val time = System.currentTimeMillis()
      for (i <- 0 until config.getSampleRate) {
        val record = new ProducerRecord[String, String]("metrics", "key", s"value_${sec + "_" + i}")
        producer.send(record, callback)
      }
      Thread.sleep(System.currentTimeMillis() - time + 1000)
    }

    producer.close()
  }
}
