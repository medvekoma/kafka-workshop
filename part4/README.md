# Session 4

- Kafka Quiz
- Testing Kafka applications
- Intro to Kafka Streams

## Questions

- **Message size** 
    - Out of the box, the Kafka brokers can handle messages up to 1MB 
    (in practice, a little bit less than 1MB) 
    with the default configuration settings 
    - Kafka is optimized for small messages of about 1K in size. 
- **Testing Kafka Applications**
    - covered in this session
    
## Quiz

- Take out your smartphones and follow instructions.

## Testing Kafka Applications

* Kafka is an infrastructure element, like a database.

| Scenario | Database aspect | Kafka aspect |
| -------- | --------------- | ------------ |
| Test the tooling | - | - |
| In memory | In memory DB | [Spring Kafka Test](https://blog.mimacom.com/testing-apache-kafka-with-spring-boot/) |
| Dockerized environment | DB running in Docker | Kafka/ZK running in Docker |
| Dev environment | Dedicated db/table | Dedicated topic |
| Dev env - live table | Delete test record | - |
| Truncate | Truncate table | Set retention.ms to 1, set it back |

* How to deal with the async nature of Kafka?
    * Demo
    
## Intro to Kafka Streams

* Demo  

## Ideas for future sessions
- Message Compression
- Acknowledgement
- Idempotent Producer
- Kafka Streams