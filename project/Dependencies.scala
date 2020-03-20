import sbt._

object Dependencies {

  val coreDependencies = Seq(
    "org.scalatest" %% "scalatest" % "3.1.0",
    "com.typesafe" % "config" % "1.4.0",
    "org.slf4j" % "slf4j-log4j12" % "1.7.30",
    "org.apache.logging.log4j" % "log4j-core" % "2.13.0",
    "org.apache.avro" % "avro" % "1.9.1",
    "com.github.scopt" %% "scopt" % "4.0.0-RC2",
    "io.kamon" %% "kamon-core" % "2.0.5",
    "io.kamon" %% "kamon-prometheus" % "2.0.1"
  )

  val kafkaDependencies = Seq(
    "org.apache.kafka" %% "kafka" % "2.4.0",
    "io.confluent" % "kafka-avro-serializer" % "5.3.0",
  )

  val cassandraDependencies = Seq(
    "com.outworkers" %% "phantom-dsl" % "2.59.0" exclude("org.slf4j", "log4j-over-slf4j")
  )
}
