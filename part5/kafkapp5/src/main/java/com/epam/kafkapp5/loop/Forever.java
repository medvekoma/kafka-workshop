package com.epam.kafkapp5.loop;

import com.epam.kafkapp5.producer.SenderApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Forever implements CommandLineRunner {

    @Autowired
    private SenderApp senderApp;

    @Override
    public void run(String... args) throws Exception {
        while(true) {
            Map<String, String> files = FileWatcher.getNextFiles();
            senderApp.send(files);
        }
    }
}
