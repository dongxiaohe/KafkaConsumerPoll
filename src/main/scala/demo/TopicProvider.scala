package demo

import java.util.Properties

import org.apache.kafka.common.serialization.StringDeserializer

trait TopicProvider {

  val topicName = "foo"
  val props = new Properties
  props.put("bootstrap.servers", "localhost:9092")

  props.put("bootstrap.servers", "localhost:9092")
  props.put("acks", "all")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  props.put("group.id", "consumer-tutorial")
  props.put("enable.auto.commit", "false")
//  props.put("max.poll.records", "10")
  props.put("key.deserializer", classOf[StringDeserializer].getName)
  props.put("value.deserializer", classOf[StringDeserializer].getName)

}
