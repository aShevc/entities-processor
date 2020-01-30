import Dependencies._

lazy val core = (project in file("core"))
  .settings(
    Commons.settings: _*
  ).settings(
  libraryDependencies ++= coreDependencies
)

lazy val generator = (project in file("generator"))
  .settings(
    Commons.settings: _*
  ).settings(
  libraryDependencies ++= (coreDependencies ++ generatorDependencies)
).dependsOn(core % "test->test;compile->compile").aggregate(core)
