package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class OutputFileWriter {
    private final File inputDir;

    public OutputFileWriter(){
        inputDir = new File("/tmp/kafka4/output");
        inputDir.mkdirs();
    }

    public void write(String fileName, String content) throws IOException {
        Path path = inputDir.toPath().resolve(fileName);
        Files.write(path, content.getBytes());
    }
}
