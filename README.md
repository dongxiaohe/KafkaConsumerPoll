We are using Kafka consumer api `poll` to retrieve topic data. However, we would like to understand more details about `poll`
This repo has some code demo to demonstrate
- The relation between `poll` and `offSet`.
- How many records poll will get from kafka topic.


## :dog: Setup
This project needs Java `8` and Scala `2.12.3`
[Install Java](https://java.com/en/download/help/index_installing.xml)

```bash
# Install Scala
$ brew install scala
$ brew install sbt

# Compile project
$./sbt.sh clean compile
```
---

## :rabbit: Running
The kafka configuration is configured in TopicProvider.scala

You can use zdi to start kafka. for myself, I installed the kafka locally, so I can easily play around with it. 

Start kafka zoo keeper and server properties.
```bash
$ bin/zookeeper-server-start.sh config/zookeeper.properties
$ bin/kafka-server-start.sh config/server.properties
```

```bash
$./sbt.sh run
```

## :tiger: The output
```
Sending value to topic: foo finished
Start to poll 10 times
start a new process to periodically poll the data
start polling from topic foo at 1
getting records count 500 start from 30 to 529 from topic: foo
thread sleep which simulates processing time (3000 ms) finished !
start polling from topic foo at 2
getting records count 500 start from 530 to 1029 from topic: foo
thread sleep which simulates processing time (3000 ms) finished !
start polling from topic foo at 3
getting records count 500 start from 1030 to 1529 from topic: foo
thread sleep which simulates processing time (3000 ms) finished !
start polling from topic foo at 4
getting records count 500 start from 1530 to 2029 from topic: foo
thread sleep which simulates processing time (3000 ms) finished !
start polling from topic foo at 5
getting records count 500 start from 2030 to 2529 from topic: foo
thread sleep which simulates processing time (3000 ms) finished !
start polling from topic foo at 6
getting records count 500 start from 2530 to 3029 from topic: foo
thread sleep which simulates processing time (3000 ms) finished !
start polling from topic foo at 7
getting records count 500 start from 3030 to 3529 from topic: foo
thread sleep which simulates processing time (3000 ms) finished !
start polling from topic foo at 8
getting records count 500 start from 3530 to 4029 from topic: foo
thread sleep which simulates processing time (3000 ms) finished !
start polling from topic foo at 9
getting records count 500 start from 4030 to 4529 from topic: foo
thread sleep which simulates processing time (3000 ms) finished !
Close the consumer connection to mimic consumer reconnects to kafka broker
Start to poll 5 times
start a new process to periodically poll the data
start polling from topic foo at 10
getting records count 500 start from 10 to 509 from topic: foo
thread sleep which simulates processing time (3000 ms) finished !
start polling from topic foo at 11
getting records count 500 start from 510 to 1009 from topic: foo
thread sleep which simulates processing time (3000 ms) finished !
start polling from topic foo at 12
getting records count 500 start from 1010 to 1509 from topic: foo
thread sleep which simulates processing time (3000 ms) finished !
start polling from topic foo at 13
getting records count 500 start from 1510 to 2009 from topic: foo
thread sleep which simulates processing time (3000 ms) finished !
start polling from topic foo at 14
getting records count 500 start from 2010 to 2509 from topic: foo
thread sleep which simulates processing time (3000 ms) finished !
Start to poll 5 times with 11 secs timeout
start a new process to periodically poll the data
start polling from topic foo at 15
no records in
start polling from topic foo at 15
getting records count 500 start from 15 to 514 from topic: foo
thread sleep which simulates processing time (11000 ms) finished !
start polling from topic foo at 16
getting records count 500 start from 515 to 1014 from topic: foo
thread sleep which simulates processing time (11000 ms) finished !
start polling from topic foo at 17
getting records count 500 start from 1015 to 1514 from topic: foo
thread sleep which simulates processing time (11000 ms) finished !
start polling from topic foo at 18
getting records count 500 start from 1515 to 2014 from topic: foo
thread sleep which simulates processing time (11000 ms) finished !
start polling from topic foo at 19
getting records count 500 start from 2015 to 2514 from topic: foo
thread sleep which simulates processing time (11000 ms) finished !
start polling from topic foo at 20
getting records count 500 start from 2515 to 3014 from topic: foo
thread sleep which simulates processing time (11000 ms) finished !
start polling from topic foo at 21
getting records count 500 start from 3015 to 3514 from topic: foo
thread sleep which simulates processing time (11000 ms) finished !
start polling from topic foo at 22
getting records count 500 start from 3515 to 4014 from topic: foo
thread sleep which simulates processing time (11000 ms) finished !
start polling from topic foo at 23
getting records count 500 start from 4015 to 4514 from topic: foo
thread sleep which simulates processing time (11000 ms) finished !
start polling from topic foo at 24
getting records count 500 start from 4515 to 5014 from topic: foo
thread sleep which simulates processing time (11000 ms) finished !
start polling from topic foo at 25
getting records count 500 start from 5015 to 5514 from topic: foo
thread sleep which simulates processing time (11000 ms) finished !
start polling from topic foo at 26
getting records count 500 start from 5515 to 6014 from topic: foo
thread sleep which simulates processing time (11000 ms) finished !
start polling from topic foo at 27
getting records count 500 start from 6015 to 6514 from topic: foo
thread sleep which simulates processing time (11000 ms) finished !
start polling from topic foo at 28
getting records count 500 start from 6515 to 7014 from topic: foo
thread sleep which simulates processing time (11000 ms) finished !
start polling from topic foo at 29
getting records count 500 start from 7015 to 7514 from topic: foo
thread sleep which simulates processing time (11000 ms) finished !

```

## :cat: The results

1. The default size of poll is maximum 500 records. which can be found in doc ([Link](https://kafka.apache.org/documentation/#upgrade_1010_notable))
2. In each poll, If consumer polls 500 records but only commit 1 record offset (Which simulates partial transaction), the current consumer ignore offset and continues to poll from 501 records. (See the output above)
 However, if consumer connection is closed, and then restart it again, it will start fetching offset records. Which totally makes sense.
3. If we have long processing time for each poll which takes longer than session timeout time, Kafka will re-balance the consumer group. 
The interesting fact is, this long-time processing consumer will not be terminated but also resume to process again later on. 