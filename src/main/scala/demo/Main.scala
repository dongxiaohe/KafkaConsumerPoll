package demo

object Main extends App {

  println("start to push records into topic")
  PushTopic()

  println("Start to poll 10 times")
  val consumer = PollTopic.process(1, 10)

  println("Close the consumer connection to mimic consumer reconnects to kafka broker")
  consumer.close()

  println("Start to poll 5 times")
  PollTopic.process(10, 15)

  println("Start to poll 5 times with 11 secs timeout")
  PollTopic.process(15, 30, 11000)
}
