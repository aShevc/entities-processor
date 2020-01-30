import sbt.{Def, ThisBuild}
import sbt.Keys.{organization, scalaVersion, scalacOptions, version}

object Commons {

  val settings: Seq[Def.Setting[_]] = Seq(
    organization := "org.metricsproc",
    version := "0.1",
    scalaVersion in ThisBuild := "2.13.1",
    scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")
  )
}