package com.epam.kafkapp5.loop;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.nio.file.StandardWatchEventKinds.*;

public class FileWatcher {

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
            if (fullPath.toFile().length() <= FILE_SIZE_LIMIT) {
                String content = new String(Files.readAllBytes(fullPath)).trim();
                result.put(fileName, content);
            }
        }
        return result;
    }
}
