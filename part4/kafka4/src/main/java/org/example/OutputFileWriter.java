package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OutputFileWriter {
    private final File inputDir;

    public OutputFileWriter(){
        inputDir = new File("/tmp/kafka4/output");
        inputDir.mkdirs();
    }

    public void writeReversed(String fileName, String content) throws IOException {
        Path path = inputDir.toPath().resolve(fileName);
        String reversed = new StringBuilder(content).reverse().toString();
        Files.write(path, reversed.getBytes());
    }
}
