name := "AkkaTask"
version := "0.1"
scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  "org.slf4j" % "slf4j-api" % "1.7.5",
  "org.slf4j" % "slf4j-simple" % "1.7.5"
)

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.6.3"
libraryDependencies += "com.typesafe.akka" %% "akka-actor-typed" % "2.6.3"
libraryDependencies += "com.typesafe.akka" %% "akka-actor-testkit-typed" % "2.6.3" % Test
libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.6.3" % Test
libraryDependencies += "org.scalamock" %% "scalamock" % "4.4.0" % Test
libraryDependencies += "org.scalatest" %% "scalatest-wordspec" % "3.2.0-M2"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.0-M2" % Test