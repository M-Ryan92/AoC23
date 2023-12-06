package org.example;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private Path getFileFromResource() throws URISyntaxException {
        URL url = getClass().getClassLoader().getResource("input.txt");
        return Paths.get(url.toURI());
    }
    public static void main(String[] args) throws URISyntaxException, IOException {
        long start = System.currentTimeMillis();
        Path filePath = new Main().getFileFromResource();
        AtomicInteger result = new AtomicInteger();
        Files.readAllLines(filePath)
                .parallelStream()
                .forEach(line -> {
                    int v = new LineProcessor(line).calculate();
                    result.addAndGet(v);
                });
        System.out.println("result is: "+ result.get());
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("time: "+ timeElapsed);
    }
}