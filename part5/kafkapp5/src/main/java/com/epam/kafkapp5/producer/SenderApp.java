package com.epam.kafkapp5.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Map;

public class SenderApp {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(Map<String, String> files) {
        for(String fileName: files.keySet()) {
            String content = files.get(fileName);
            kafkaTemplate.send("test", fileName, content);
        }
    }
}
