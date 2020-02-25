package com.epam.kafkacon;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.stream.StreamSupport;

public class Program {
    public static void main(String[] args) {
        try (KafkaClientConsumer kcConsumer = new KafkaClientConsumer()) {
            KafkaConsumer<String, String> consumer = kcConsumer.consumer();
            consumer.subscribe(Collections.singletonList("apps"));
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));
                StreamSupport.stream(records.spliterator(), true)
                    .map(record -> TopLine.fromString(record.value()))
                    .forEach(TopLine::dump);
                consumer.commitAsync();
            }
        }
    }
}
