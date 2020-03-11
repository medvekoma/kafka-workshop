package com.epam.kafkapp5;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AppTests {

	@Autowired
	private EmbeddedKafkaBroker embeddedKafkaBroker;

	private DefaultKafkaConsumerFactory<String, String> consumerFactory;
	private DefaultKafkaProducerFactory<String, String> producerFactory;

	@BeforeTestClass
	void setup() {
		Map<String, Object> consumerProps = KafkaTestUtils.consumerProps(
				"kafka5-test-consumer", "false", embeddedKafkaBroker);
//		Map<String, Object> configs = new HashMap<>(consumerProps);
		consumerFactory = new DefaultKafkaConsumerFactory<>(
				consumerProps, new StringDeserializer(), new StringDeserializer());
		Map<String, Object> producerProps = KafkaTestUtils.producerProps(embeddedKafkaBroker);
//		Map<String, Object> configs = new HashMap<>(producerProps);
		producerFactory = new DefaultKafkaProducerFactory<>(
				producerProps, new StringSerializer(), new StringSerializer());
	}

	@Test
	void filesReceivedTests() {
		String fileName = "a.txt";
		String content = "abcde";
		Map<String, String> files = new HashMap<>();
		files.put(fileName, content);

		try(Consumer<String, String> consumer = consumerFactory.createConsumer()) {
//			app.receivedFiles(files);

			ConsumerRecord<String, String> record = KafkaTestUtils.getSingleRecord(consumer, "test");
			assertThat(record.key()).isEqualTo("a.txt");
			assertThat(record.value()).isEqualTo("edcba");
		}
	}

}
