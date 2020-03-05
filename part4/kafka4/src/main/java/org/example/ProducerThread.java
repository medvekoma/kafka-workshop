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

public class ProducerThread extends Thread {

    private final static Integer FILE_SIZE_LIMIT = 10;

    public void run() {

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        try(KafkaProducer<String, String> producer = new KafkaProducer<>(props)) {
            waitForFiles(producer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void waitForFiles(KafkaProducer<String, String> producer) throws IOException {
        File inputDir = new File("/tmp/kafka4/input");
        inputDir.mkdirs();
        WatchService watcher = FileSystems.getDefault().newWatchService();
        inputDir.toPath().register(watcher, ENTRY_CREATE, ENTRY_MODIFY);
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

                if (fullPath.toFile().length() <= FILE_SIZE_LIMIT) {
                    String text = new String(Files.readAllBytes(fullPath)).trim();
                    producer.send(new ProducerRecord<>("test", filename.toString(), text));
                }
            }

            boolean valid = key.reset();
            if (!valid) {
                break;
            }
        }
    }
}
