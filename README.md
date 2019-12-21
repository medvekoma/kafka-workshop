# Kafka Workshop

## Cluster management

In the `demo` folder there is a dockerized kafka environment.

```bash
# Create cluster
docker-compose up -d

# Delete cluster
docker-compose down
```

## Cluster operations

| Operation | Standard tooling | Kafkacat |
| ---       | ---              |  -----   |
| Describe cluster | `kafka-topics --describe` | `kafkacat -L` |
| Consume messages | `kafka-console-consumer`  | `kafkacat -C` |
| Produce messages | `kafka-console-producer`  | `kafkacat -P` |
 
