# Session 3

Consumer offsets, Delivery semantics

## Looking back

### Notions
* Topic, Partition, Replication
* Broker, Zookeeper
* Producer, Consumer
* Scalability
* Consumer Groups
* Message ordering

## Consumer offsets

```bash
# Create app topic
kafka-topics --bootstrap-server localhost:9092 --create \
    --topic apps \
    --partitions 2 \
    --replication-factor 2

# Check top-json command
./top-json.sh

# Produce 10 messages
./top-json.sh | kafkacat -b localhost:9092 -t apps -P -K '\t' -c 10

# Check content
kafka-console-consumer \
    --bootstrap-server localhost:9092 \
    --topic apps \
    --group top-consumer \
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
    --group top-consumer
```

### Watch video

[Consumer offsets & Delivery Sema](https://www.linkedin.com/learning/learn-apache-kafka-for-beginners/consumer-offsets-and-delivery-semantics)
