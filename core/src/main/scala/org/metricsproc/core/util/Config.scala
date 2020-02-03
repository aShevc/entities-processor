package org.metricsproc.core.util

import com.typesafe.config.{ConfigFactory, Config => TypesafeConfig}
import scala.jdk.CollectionConverters._

trait Config {

  def getConfigPrefix: String

  lazy val config: TypesafeConfig = ConfigFactory.load()

  // config helpers
  protected def getString(path: String): Option[String] = get(path, _.getString)

  protected def getStringList(path: String): Option[List[String]] = get(path, _.getStringList).map(x => x.asScala.toList)

  protected def getInt(path: String): Option[Int] = get(path, _.getInt)

  protected def getLong(path: String): Option[Long] = get(path, _.getLong)

  protected def getDouble(path: String): Option[Double] = get(path, _.getDouble)

  protected def getBoolean(path: String): Option[Boolean] = get(path, _.getBoolean)

  protected def get[T](path: String, getter: TypesafeConfig => String => T): Option[T] = {
    if (config.hasPath(path)) Some(getter(config)(path))
    else None
  }
}
