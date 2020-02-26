# Session 3

Consumer offsets, Delivery semantics

## Looking back

### Questions / Comments
* [Security & Encryption](https://medium.com/@stephane.maarek/introduction-to-apache-kafka-security-c8951d410adf)
* Kafka tools in this repo

### Notions
* Topic, Partition, Replication
* Broker, Zookeeper
* Producer, Consumer
* Scalability
* Consumer Groups
* Message ordering

## Consumer offsets

```bash
# Create topic
kafka-topics --bootstrap-server localhost:9092 --create \
    --topic demo \
    --partitions 2 \
    --replication-factor 2

# Check message generator
./messages.sh

# Produce 10 messages
./messages.sh | kafkacat -b localhost:9092 -t demo -P -K '\t' -c 10

# Check content
kafka-console-consumer \
    --bootstrap-server localhost:9092 \
    --topic demo \
    --group demo-consumer \
    --from-beginning

# Try again - for the second run nothing is returned.
```

Why?

.

.

.

.

.

Consumer group tracks progress and keeps a the current offset.

```bash
kafka-consumer-groups \
    --bootstrap-server localhost:9092 \
    --describe \
    --group demo-consumer
```

The consumer can be down for a long time(*) and can still continue from where it left off.
- Unless topic retention time is exceeded (by default 7 days)
- Unless no message is processed for `offsets.retention.minutes` (7 days since 2.0, 1 day earlier)

## Consumer offsets & Delivery semantics

* [Watch video](https://www.linkedin.com/learning/learn-apache-kafka-for-beginners/consumer-offsets-and-delivery-semantics)

### Consumer offsets
* Consumer offsets are stored in a topic `__consumer_offsets`
* Committing offsets (automatic or manual)
* The consumer will continue from the stored offsets when restarted

### Message Delivery Semantics

There are two hard problems in distrubuted systems:

    - 2. Exactly-once delivery
    - 1. Guaranteed order of messages
    - 2. Exactly-once delivery 

* **None:**
    - By default we don't have any guarantee 
* **At most once:** 
    - Commit as soon as the message is **received**
        - Set `enable.auto.commit` to `true`  & `auto.commit.interval.ms` to a small value (?)
        - Set `enable.auto.commit` to `false` & commit manually when you receive the message
* **At least once (preferred):**
    - Commit when the message is **processed**
        - Set `enable.auto.commit` to `false` & commit offsets when after successful processing
        - Set `enable.auto.commit` to `true`  & `auto.commit.interval.ms` to a large value (?)
    - Prepare for re-processing (idempotence)
* **Exactly once:**
    - Limited availability, skipping now

In the real world you will go with **At least once** delivery.
    
# Homework

* Watch videos (9 mins total + 7 mins bonus)
    - [Consumer offsets & Delivery Semantics](https://www.linkedin.com/learning/learn-apache-kafka-for-beginners/consumer-offsets-and-delivery-semantics)
    - [Delivery Semantics for consumers]( https://www.linkedin.com/learning/learn-apache-kafka-for-beginners/delivery-semantics-for-consumers)
    - [Bonus: Idempotence](https://www.linkedin.com/learning/learn-apache-kafka-for-beginners/consumer-part-3-idempotence)
    
* Try the `kafkacon` application
    - Run it with some messages in the `demo` topic.
    - Make it fail
    - Implement `At least once` delivery.
