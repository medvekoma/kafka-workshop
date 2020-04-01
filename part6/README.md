# Introduction to Kafka Streams

## Produce messages

```bash
# Create topics
kafka-topics --bootstrap-server localhost:9092 --create --topic code \
    --partitions 1 --replication-factor 1 
kafka-topics --bootstrap-server localhost:9092 --create --topic name \
    --partitions 1 --replication-factor 1 
kafka-topics --bootstrap-server localhost:9092 --create --topic codename \
    --partitions 1 --replication-factor 1 

# Test messages
./messages.sh

# Run application

# Start a consumer for the result topic
kafkacat -b localhost:9092 -C -t codename -K ', ' -q

# In a different window start producing messages to the `code` topic
./messages.sh | kafkacat -b localhost:9092 -P -t code

# In a third window start setting up country names
kafkacat -b localhost:9092 -P -t name -K ','
```
```csv
HU,Hungary
PL,Poland
```
Change Names
```csv
PL,Polska
HU,Magyarország
```
