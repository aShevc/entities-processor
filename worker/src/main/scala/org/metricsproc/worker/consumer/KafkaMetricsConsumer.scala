package org.metricsproc.worker.consumer

import java.time.Duration
import java.util.{Properties, UUID}

import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig
import org.apache.avro.generic.GenericRecord
import org.apache.avro.specific.SpecificData
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord, KafkaConsumer}
import org.apache.kafka.common.errors.SerializationException
import org.metricsproc.metric.Metric
import org.metricsproc.worker.MetricsWorker
import org.metricsproc.worker.util.WorkerConfig
import org.slf4j.LoggerFactory

import scala.jdk.CollectionConverters._
import scala.util.{Failure, Try}

trait KafkaMetricsConsumer extends MetricsConsumer {
  this: MetricsWorker =>

  private val log = LoggerFactory.getLogger(classOf[KafkaMetricsConsumer])

  val config: WorkerConfig

  override def consume(): Try[Unit] = {
    log.info(s"Starting Kafka metrics consumer with config:\n$config")

    val props = new Properties()

    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getKafkaServer)
    // In case if we want to consume from beginning, we will simply assign a consumer group with a custom name
    val consumerGroup = if (config.getConsumeFromBeginning) {
      props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
      config.getKafkaConsumerGroup + UUID.randomUUID()
    } else config.getKafkaConsumerGroup
    props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroup)
    props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);

    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "io.confluent.kafka.serializers.KafkaAvroDeserializer")
    props.put("schema.registry.url", config.getSchemaRegistryUrl)

    val consumer = new KafkaConsumer[String, Any](props)

    consumer.subscribe(List(config.getKafkaTopic).asJava)

    var i = 0

    while (true) {
      Try {
        val records = consumer.poll(Duration.ofMillis(1000))
        records.forEach { msg =>
          i += 1
          // We will consume specific records here. This approach works in case if the schema does not change in the
          // future. Otherwise, it makes sense to use GenericRecord instance and place a hint into the GenericRecord
          // which will say what is the message format.
          val record = SpecificData.get.deepCopy(Metric.SCHEMA$, msg.asInstanceOf[ConsumerRecord[String, GenericRecord]].value())
            .asInstanceOf[Metric]
          processMetrics(List(record))
        }
      } recover {
        case se: SerializationException =>
          log.warn("Message of unknown format detected in the topic", se)
          if (consumer.assignment().isEmpty) {
            //should not happen
            return Failure(se)
          } else {
            consumer.assignment().forEach { tp =>
              val currentPosition = consumer.position(tp)
              log.info(s"Current offset: $currentPosition. Attempting to move forward")
              consumer.seek(tp, currentPosition + 1)
            }
          }
        case exc =>
          log.error("Could not consume a message. Exiting")
          consumer.close()
          return Failure(exc)
      }
    }
    Try()
  }
}
