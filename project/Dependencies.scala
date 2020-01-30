import sbt._

object Dependencies {

  val coreDependencies = Seq(
    "org.scalatest" %% "scalatest" % "3.1.0",
    "org.apache.kafka" %% "kafka" % "2.4.0",
    "com.typesafe" % "config" % "1.4.0",
    "org.slf4j" % "slf4j-log4j12" % "1.7.30",
    "org.apache.logging.log4j" % "log4j-core" % "2.13.0"
  )

  val generatorDependencies = Seq(
    "com.github.scopt" %% "scopt" % "4.0.0-RC2"
  )

  val cassandraWorkerDependencies = Seq(

  )
}
