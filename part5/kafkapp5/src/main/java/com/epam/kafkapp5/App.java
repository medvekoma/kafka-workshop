package com.epam.kafkapp5;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Map;

@SpringBootApplication
public class App implements CommandLineRunner {

	public static Logger logger = LoggerFactory.getLogger(App.class);
	private final OutputFileWriter outputFileWriter = new OutputFileWriter();

	@Autowired
	private KafkaTemplate<String, String> template;

	public static void main(String[] args) {
		SpringApplication.run(App.class, args).close();
	}

	@Override
	public void run(String... args) throws Exception {
		while(true) {
			Map<String, String> files = ProducerApp.getNextFiles();
			receivedFiles(files);
		}
	}

	public void receivedFiles(Map<String, String> files) {
		for(String fileName: files.keySet()) {
			String content = files.get(fileName);
			template.send("test", fileName, content);
		}
	}

	@KafkaListener(topics = "test")
	public void listen(ConsumerRecord<String, String> r) throws Exception {
		outputFileWriter.writeReversed(r.key(), r.value());
	}

}
