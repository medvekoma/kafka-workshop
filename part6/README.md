# Introduction to Kafka Streams

## Produce messages

```bash
# Create topics
kafka-topics --bootstrap-server localhost:9092 --create --topic entity \
    --partitions 1 --replication-factor 1 
kafka-topics --bootstrap-server localhost:9092 --create --topic country \
    --partitions 1 --replication-factor 1 
kafka-topics --bootstrap-server localhost:9092 --create --topic enriched \
    --partitions 1 --replication-factor 1 

# Run application

# Start a consumer for the result topic
kafkacat -b localhost:9092 -C -t enriched -q

# In a different window start producing messages to the `entity` topic
./messages.sh
./messages.sh | kafkacat -b localhost:9092 -P -t entity

# In a third window start setting up country names
kafkacat -b localhost:9092 -P -t country -K ','
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
