name := "POC-KafkaConsumerPollAndOffset"

version := "0.1"

scalaVersion := "2.12.4"

libraryDependencies += "com.typesafe.akka" %% "akka-stream-kafka" % "0.17"

mainClass in(Compile, run) := Some("demo.Main")