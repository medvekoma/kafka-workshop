package com.epam.kafkapp5;

import org.springframework.kafka.core.KafkaTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.nio.file.StandardWatchEventKinds.*;

public class ProducerApp {

    private final static Integer FILE_SIZE_LIMIT = 10;

    public static Map<String, String> getNextFiles() throws IOException {
        File inputDir = new File("/tmp/kafka5/input");
        inputDir.mkdirs();
        WatchService watcher = FileSystems.getDefault().newWatchService();
        inputDir.toPath().register(watcher, ENTRY_CREATE, ENTRY_MODIFY);

        // wait for key to be signaled
        WatchKey key;
        try {
            key = watcher.take();
        } catch (InterruptedException x) {
            return new HashMap<>();
        }

        List<Path> paths = key.pollEvents().stream()
                .filter(e -> e.kind() != OVERFLOW)
                .map(e -> (WatchEvent<Path>) e)
                .map(e -> e.context())
                .collect(Collectors.toList());

        Map<String, String> result = new HashMap<>();
        for (Path path: paths) {
            String fileName = path.toString();
            Path fullPath = inputDir.toPath().resolve(fileName);
            String content = new String(Files.readAllBytes(fullPath)).trim();
            result.put(fileName, content);
        }
        return result;
    }

    public static void waitForFiles(KafkaTemplate<String, String> template) throws IOException {
        File inputDir = new File("/tmp/kafka5/input");
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
                    template.send("test", filename.toString(), text);
                }
            }

            boolean valid = key.reset();
            if (!valid) {
                break;
            }
        }
    }
}
