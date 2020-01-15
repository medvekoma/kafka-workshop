# Manage topics

## Using `kafka-topics`

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

## Using `kafkacat`

```bash
# Describe topics
kafkacat -b localhost:9092 -L

# Describe topic
kafkacat -b localhost:9092 -L -t test2
```

# Produce messages

```bash
# Using the official tooling `kafka-console-producer`
cat content.txt | kafka-console-producer \
    --broker-list localhost:9092 \
    --topic test
    
# Using `kafkacat`
kafkacat -b localhost:9092 -P -t test -l content.txt
```

# Consume messages

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
