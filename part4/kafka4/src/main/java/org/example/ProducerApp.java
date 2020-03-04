package org.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Properties;

import static java.nio.file.StandardWatchEventKinds.*;

public class ProducerApp {

    private final static Integer FILE_SIZE = 10;

    public static void run() throws IOException {

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        try(KafkaProducer<String, String> producer = new KafkaProducer<>(props)) {
            waitForFiles(producer);
        }

    }

    private static void waitForFiles(KafkaProducer<String, String> producer) throws IOException {
        File inputDir = new File("/tmp/kafka4/input");
        inputDir.mkdirs();
        WatchService watcher = FileSystems.getDefault().newWatchService();
        inputDir.toPath().register(watcher, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);
        while (true) {
            // wait for key to be signaled
            WatchKey key;
            try {
                key = watcher.take();
            } catch (InterruptedException x) {
                return;
            }

            for (WatchEvent<?> event: key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();

                if (kind == OVERFLOW) {
                    continue;
                }

                WatchEvent<Path> ev = (WatchEvent<Path>) event;
                Path filename = ev.context();
                Path fullPath = inputDir.toPath().resolve(filename);

                boolean clearEntry = kind == ENTRY_DELETE || fullPath.toFile().length() > FILE_SIZE;

                String text = clearEntry
                        ? ""
                        : new String(Files.readAllBytes(fullPath)).trim();

                producer.send(new ProducerRecord<>("test", filename.toString(), text));
            }

            boolean valid = key.reset();
            if (!valid) {
                break;
            }
        }
    }
}
