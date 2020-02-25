package com.epam.kafkacon;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.stream.StreamSupport;

public class Program {
    public static void main(String[] args) {
        try (KafkaClientConsumer kcConsumer = new KafkaClientConsumer()) {
            KafkaConsumer<String, String> consumer = kcConsumer.consumer();
            consumer.subscribe(Collections.singletonList("demo"));
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                consumeStream(records);
//                consumer.commitAsync();
            }
        }
    }

    private static void consumeStream(ConsumerRecords<String, String> records) {
        StreamSupport.stream(records.spliterator(), true)
            .map(record -> DemoLine.fromString(record.value()))
            .forEach(DemoLine::prettyPrint);
    }

    private static void consumeLoop(ConsumerRecords<String, String> records) {
        for (ConsumerRecord<String, String> record: records) {
            DemoLine demoLine = DemoLine.fromString(record.value());
            demoLine.prettyPrint();
        }
    }
}
