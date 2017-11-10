package demo

import org.apache.kafka.clients.consumer.{Consumer, KafkaConsumer, OffsetAndMetadata}
import org.apache.kafka.common.TopicPartition

import scala.collection.JavaConverters._
import scala.util.{Failure, Try}

object PollTopic extends App with TopicProvider {

  def process(start: Long, stopAt: Long, calculationTime: Long = 3000): Consumer[String, String] = {
    println("start a new process to periodically poll the data")
    var count = start
    val consumer = new KafkaConsumer[String, String](props)
    consumer.subscribe(List(topicName).asJava)
    while(count < stopAt) {
      Try {
        println(s"start polling from topic $topicName at $count")
        val records = consumer.poll(1000)
        val countRecords = records.count()
        if (countRecords > 0) {
          val start = records.iterator().next().value().toLong
          val end = start + countRecords - 1

          println(s"getting records count $countRecords start from $start to $end from topic: $topicName")

          consumer.commitSync(Map(new TopicPartition(topicName, 0) -> new OffsetAndMetadata(count)).asJava)

          count = count + 1

          Thread.sleep(calculationTime)
          println(s"thread sleep which simulates processing time ($calculationTime ms) finished !")
        } else {
          println("no records in")
        }

      } match {
        case Failure(ex) => println(ex.getMessage); sys.exit(0)
        case _ =>
      }
    }

    consumer
  }

}
