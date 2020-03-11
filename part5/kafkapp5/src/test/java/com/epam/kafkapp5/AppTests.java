package com.epam.kafkapp5;

import com.epam.kafkapp5.producer.SenderApp;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = "test")
class AppTests {

	@Autowired
	private EmbeddedKafkaBroker embeddedKafkaBroker;

	@Autowired
	private SenderApp senderApp;

	private DefaultKafkaConsumerFactory<String, String> consumerFactory;
	private DefaultKafkaProducerFactory<String, String> producerFactory;

	@BeforeTestClass
	void setup() {
		Map<String, Object> consumerProps = KafkaTestUtils.consumerProps(
				"kafka5-test-consumer", "false", embeddedKafkaBroker);
		Map<String, Object> consumerConfig = new HashMap<>(consumerProps);
		consumerFactory = new DefaultKafkaConsumerFactory<>(
				consumerConfig, new StringDeserializer(), new StringDeserializer());
		Map<String, Object> producerProps = KafkaTestUtils.producerProps(embeddedKafkaBroker);
		Map<String, Object> producerConfig = new HashMap<>(producerProps);
		producerFactory = new DefaultKafkaProducerFactory<>(
				producerConfig, new StringSerializer(), new StringSerializer());
	}

	@Test
	void filesReceivedTests() {
		String fileName = "a.txt";
		String content = "abcde";
		Map<String, String> files = new HashMap<>();
		files.put(fileName, content);

		try(Consumer<String, String> consumer = consumerFactory.createConsumer()) {
			senderApp.send(files);

			ConsumerRecord<String, String> record = KafkaTestUtils.getSingleRecord(consumer, "test");
			assertThat(record.key()).isEqualTo("a.txt");
			assertThat(record.value()).isEqualTo("edcba");
		}
	}

}
