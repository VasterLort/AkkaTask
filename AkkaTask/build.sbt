name := "AkkaTask"
version := "0.1"
scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  "org.slf4j" % "slf4j-api" % "1.7.5",
  "org.slf4j" % "slf4j-simple" % "1.7.5"
)

libraryDependencies ++= Seq(
  "javax.ws.rs" % "javax.ws.rs-api" % "2.0.1",
  "com.github.swagger-akka-http" %% "swagger-akka-http" % "2.0.4",
  "com.github.swagger-akka-http" %% "swagger-scala-module" % "2.0.6",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.10.3",
  "io.swagger.core.v3" % "swagger-core" % "2.1.1",
  "io.swagger.core.v3" % "swagger-annotations" % "2.1.1",
  "io.swagger.core.v3" % "swagger-models" % "2.1.1",
  "io.swagger.core.v3" % "swagger-jaxrs2" % "2.1.1",
  "com.typesafe.akka" %% "akka-http" % "10.1.11",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.11",
  "com.typesafe.akka" %% "akka-stream" % "2.6.3",
  "com.typesafe.akka" %% "akka-slf4j" % "2.6.3",
  "ch.megard" %% "akka-http-cors" % "0.4.2",
)

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.6.3"
libraryDependencies += "com.typesafe.akka" %% "akka-actor-typed" % "2.6.3"
libraryDependencies += "com.typesafe.akka" %% "akka-persistence-typed" % "2.6.3"
libraryDependencies += "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8"
libraryDependencies += "org.iq80.leveldb"  % "leveldb" % "0.7"
libraryDependencies += "com.typesafe.akka" %% "akka-actor-testkit-typed" % "2.6.3" % Test
libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.6.3" % Test
libraryDependencies += "org.scalatest" %% "scalatest-wordspec" % "3.2.0-M2"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.0-M2" % Test
libraryDependencies += "org.scalamock" %% "scalamock" % "4.4.0" % Test
