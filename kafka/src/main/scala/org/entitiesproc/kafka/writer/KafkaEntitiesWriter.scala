package org.entitiesproc.kafka.writer

import java.util.Properties

import io.confluent.kafka.serializers.KafkaAvroSerializer
import kamon.Kamon
import org.apache.kafka.clients.producer.{Callback, KafkaProducer, ProducerRecord, RecordMetadata}
import org.entitiesproc.core.writer.EntitiesWriter
import org.entitiesproc.entity.Entity
import org.entitiesproc.kafka.util.KafkaWriterConfig
import org.slf4j.LoggerFactory

trait KafkaEntitiesWriter extends EntitiesWriter with KafkaWriterConfig {

  private val log = LoggerFactory.getLogger(classOf[KafkaEntitiesWriter])

  val props = new Properties()

  private lazy val writesCounter = Kamon.counter("kafka-writes").withoutTags()

  props.put("bootstrap.servers", getKafkaWriterServer)
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", classOf[KafkaAvroSerializer])
  props.put("schema.registry.url", getWriterSchemaRegistryUrl)

  val callback: Callback = (_: RecordMetadata, exception: Exception) => {
    if (exception != null) {
      log.warn(s"could not produce a record: $exception")
    } else {
      writesCounter.increment()
    }
  }

  val producer = new KafkaProducer[String, Entity](props)

  log.info(s"Starting Kafka entities writer with config:\n$getKafkaWriterConfig")

  override def write(entity: Entity): Unit = {
    producer.send(new ProducerRecord(getKafkaWriterTopic, entity.getDeviceId.toString, entity), callback)
  }

  override def closeWriter(): Unit = {
    producer.close()
  }
}
