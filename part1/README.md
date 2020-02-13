# What is Kafka

  * Scalable, Persistent Message Queue
  
## Message Queue

1. Decouple source and target systems

   ![Message Queue](../img/mq.png)
   (like messaging instead of phone calls)
   
1. Reduce integration complexity

   ![Integrations](../img/integrations.png)
   * Each target system (consumer) is tracking its own progress.

## Persistent

   * Target system can be unavailable for hours (days).
   * Messages are stored on disk. Retention time.

## Scalable

   * Designed with scalability in mind. How to process and increasing number of messages? (millions of messages / second)
   
### Partitioning
   * Distribute the data across multiple machines (partitioning)  
   ![Paritions](../img/partitions.png)
   * Machines that hold the data are called Brokers.
   ![Brokers](../img/broker-partition.png)
   
### Replication
   * The more nodes we have, the more likely is to have a breakdown
   ![Replication](../img/broker-replica.png)

# Take away notions
  * Topic
  * Partition
  * Broker
  * Zookeeper
  * Producer
  * Consumer
   

# Command-line tools

| Operation | Standard tooling | Kafkacat |
| ---       | ---              |  -----   |
| Describe topics  | `kafka-topics --describe` | `kafkacat -L` |
| Consume messages | `kafka-console-consumer`  | `kafkacat -C` |
| Produce messages | `kafka-console-producer`  | `kafkacat -P` |

## Manage topics

### Using `kafka-topics`

```bash
# Create topic
kafka-topics --bootstrap-server localhost:9092 \
    --create \
    --topic test2 \
    --partitions 2 \
    --replication-factor 2
    
# List topics
kafka-topics --bootstrap-server localhost:9092 --list
    
# Describe topics
kafka-topics --bootstrap-server localhost:9092 --describe

# Describe topic
kafka-topics --bootstrap-server localhost:9092 --describe \
    --topic test2
```

### Using `kafkacat`

```bash
# Describe topics
kafkacat -b localhost:9092 -L

# Describe topic
kafkacat -b localhost:9092 -L -t test2
```

## Produce messages

```bash
# Using the official tooling `kafka-console-producer`
cat content.txt | kafka-console-producer \
    --broker-list localhost:9092 \
    --topic test
    
# Using `kafkacat`
kafkacat -b localhost:9092 -P -t test -l content.txt
```

## Consume messages

```bash
# Using the official tooling
kafka-console-consumer \
    --bootstrap-server localhost:9092 \
    --topic test \
    --from-beginning
    
# Using kafkacat
kafkacat -b localhost:9092 -t test

# Using `kafkacat`, exitting when finished
kafkacat -b localhost:9092 -t test -e
```

# Homework

* Watch the following lectures (15 mins total):
   - [Apache Kafka in 5 minutes](https://www.linkedin.com/learning/learn-apache-kafka-for-beginners/apache-kafka-in-five-minutes).
   - [Topics, Partitions and Offsets](https://www.linkedin.com/learning/learn-apache-kafka-for-beginners/topics-partitions-and-offsets).
   - [Brokers and Topics](https://www.linkedin.com/learning/learn-apache-kafka-for-beginners/brokers-and-topics)
   
* Exercise
   - Setup a kafka cluster on your machine (you can use the dockerized environment).
   - Create a topic `entries` with 2 partitions and replication factor of two.
   - Import all lines from `entries.json` into this new topic.
   - Read all lines from this topic.
