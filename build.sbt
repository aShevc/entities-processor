import Dependencies._

lazy val core = (project in file("core"))
  .settings(
    Commons.settings: _*
  ).settings(
  resolvers += "RedHat repository" at "https://maven.repository.redhat.com/earlyaccess/all/",
  libraryDependencies ++= coreDependencies
)

lazy val kafka = (project in file("kafka"))
  .settings(
    Commons.settings: _*
  ).settings(
  libraryDependencies ++= (coreDependencies ++ kafkaDependencies)
).dependsOn(core % "test->test;compile->compile").aggregate(core)

lazy val cassandra = (project in file("cassandra"))
  .settings(
    Commons.settings: _*
  ).settings(
  libraryDependencies ++= (coreDependencies ++ cassandraDependencies)
).dependsOn(core % "test->test;compile->compile").aggregate(core)
