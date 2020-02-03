import Dependencies._

lazy val core = (project in file("core"))
  .settings(
    Commons.settings: _*
  ).settings(
  resolvers += "RedHat repository" at "https://maven.repository.redhat.com/earlyaccess/all/",
  libraryDependencies ++= coreDependencies
)

lazy val generator = (project in file("generator"))
  .settings(
    Commons.settings: _*
  ).settings(
  libraryDependencies ++= (coreDependencies ++ generatorDependencies)
).dependsOn(core % "test->test;compile->compile").aggregate(core)

lazy val worker = (project in file("worker"))
  .settings(
    Commons.settings: _*
  ).settings(
  libraryDependencies ++= (coreDependencies ++ workerDependencies)
).dependsOn(core % "test->test;compile->compile").aggregate(core)

lazy val cassandraWorker = (project in file("cassandra-worker"))
  .settings(
    Commons.settings: _*
  ).settings(
  libraryDependencies ++= (coreDependencies ++ cassandraWorkerDependencies)
).dependsOn(worker % "test->test;compile->compile").aggregate(worker)
