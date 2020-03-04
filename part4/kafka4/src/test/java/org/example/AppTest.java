package org.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeConsumerGroupsResult;
import org.apache.kafka.clients.admin.ListConsumerGroupOffsetsResult;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class AppTest
{
    @Test
    public void integrationTest() throws IOException, ExecutionException, InterruptedException {

        // Prepare consumer (observe latest)
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, String.format("it-group-%s", UUID.randomUUID()));
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Collections.singletonList("test"));

            // Create file
            Path inputPath = new File("/tmp/kafka4/input/it.txt").toPath();
            Files.write(inputPath, "something".getBytes());

            // poll records
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));
            List<String> values = StreamSupport.stream(records.spliterator(), true)
                    .filter(record -> Objects.equals(record.key(), "it.txt"))
                    .map(record -> record.value())
                    .collect(Collectors.toList());

            assertEquals("Message count doesn't match", 1, values.size());
            assertEquals("Message value doesn't match", "something", values.get(0));
            System.out.println("Message arrived to Kafka.");

            TopicPartition topicPartition = new TopicPartition("test", 0);
            long itemOffset = consumer.endOffsets(Collections.singleton(topicPartition)).get(topicPartition);
            System.out.println(String.format("Current offset is: %d", itemOffset));

            Properties adminProps = new Properties();
            adminProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            AdminClient adminClient = AdminClient.create(adminProps);

            long consumerOffset;
            do {
                Thread.sleep(100);
                ListConsumerGroupOffsetsResult result = adminClient.listConsumerGroupOffsets("kafka4-group");
                consumerOffset = result.partitionsToOffsetAndMetadata().get()
                        .get(topicPartition).offset();
                System.out.println(String.format("Offsets - item: %d, consumer: %d", itemOffset, consumerOffset));
            } while (consumerOffset < itemOffset);
            System.out.println("Consumer group processed the message");

            File outputFile = new File("/tmp/kafka4/output/it.txt");
            while (!outputFile.exists()) {
                System.out.println("Waiting for file to be created...");
            }
            String content = new String(Files.readAllBytes(outputFile.toPath()));
            System.out.println(String.format("File exists with content: %s", content));

            assertEquals("Content doesn't match", "gnihtemos", content);

            // cleanup
            Files.delete(inputPath);
            Files.delete(outputFile.toPath());
        }
    }
}
