package by.itechart.config

import com.typesafe.config.ConfigFactory

case class ConnectionConfig(
                             port: Int
                           )

object ConnectionConfig {
  private lazy val configLoader = ConfigFactory.load("application.conf")
  lazy val setConfigValues = ConnectionConfig(configLoader.getInt("connection.port"))
}