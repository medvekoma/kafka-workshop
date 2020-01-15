package com.epam.kafkacon;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;

public class Program {
    public static void main(String[] args) {
        try (KafkaClientConsumer kcConsumer = new KafkaClientConsumer()) {
            KafkaConsumer<String, String> consumer = kcConsumer.consumer();
            consumer.subscribe(Arrays.asList("test"));
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record.value());
            }
        }
    }
}
