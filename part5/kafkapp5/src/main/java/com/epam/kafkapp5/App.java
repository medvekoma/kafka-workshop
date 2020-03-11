package com.epam.kafkapp5;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class App implements CommandLineRunner {

	public static Logger logger = LoggerFactory.getLogger(App.class);

	@Autowired
	private KafkaTemplate<String, String> template;

	public static void main(String[] args) {
		SpringApplication.run(App.class, args).close();
	}

	private final CountDownLatch latch = new CountDownLatch(3);

	@Override
	public void run(String... args) throws Exception {
		this.template.send("myTopic", 0, "key1", "foo1");
		this.template.send("myTopic", 0, "key2", "foo2");
		this.template.send("myTopic", 0, "key3", "foo3");
		latch.await(60, TimeUnit.SECONDS);
		logger.info("All received");
	}

	@KafkaListener(topics = "myTopic")
	public void listen(ConsumerRecord<String, String> r) throws Exception {
		logger.info(">>> {}:{}", r.key(), r.value());
		latch.countDown();
	}

}
