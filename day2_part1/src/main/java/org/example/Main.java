package org.example;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private Path getFileFromResource(String fileName) throws URISyntaxException {
        URL url = getClass().getClassLoader().getResource(fileName);
        return Paths.get(url.toURI());
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        long start = System.currentTimeMillis();
        Path filePath = new Main().getFileFromResource("input.txt");
        Bag bag = new Bag(12, 13, 14);

        AtomicInteger counter = new AtomicInteger(0);
        Files.readAllLines(filePath)
                .parallelStream()
                .forEach(line -> {
                    int a = bag.processResults(line);
                    counter.addAndGet(a);
                });

        System.out.println(counter.get());
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("time: "+ timeElapsed);
    }
}