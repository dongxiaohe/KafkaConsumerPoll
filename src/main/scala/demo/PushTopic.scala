package demo

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

object PushTopic extends App with TopicProvider {
  def apply(): Unit = {
    val producer = new KafkaProducer[String, String](props)

    (1 to 30000).foreach(number => producer.send(new ProducerRecord[String, String](topicName, number.toString)))
    producer.close()

    println(s"Sending value to topic: $topicName finished")
  }
}
