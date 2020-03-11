package com.epam.kafkapp5.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

public class ReceiverApp {

    private final OutputFileWriter outputFileWriter = new OutputFileWriter();

    @KafkaListener(topics = "test")
    public void listen(ConsumerRecord<String, String> r) throws Exception {
        outputFileWriter.writeReversed(r.key(), r.value());
    }
}
