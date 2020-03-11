package com.epam.kafkapp5.consumer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class OutputFileWriter {
    private final File inputDir;

    public OutputFileWriter(){
        inputDir = new File("/tmp/kafka5/output");
        inputDir.mkdirs();
    }

    public void writeReversed(String fileName, String content) throws IOException {
        Path path = inputDir.toPath().resolve(fileName);
        String reversed = new StringBuilder(content).reverse().toString();
        Files.write(path, reversed.getBytes());
    }
}
