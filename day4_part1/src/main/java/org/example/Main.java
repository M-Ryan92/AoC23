package org.example;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    private Path getFileFromResource() throws URISyntaxException {
        URL url = getClass().getClassLoader().getResource("input.txt");
        return Paths.get(url.toURI());
    }
    public static void main(String[] args) throws URISyntaxException, IOException {
        long start = System.currentTimeMillis();
        Path filePath = new Main().getFileFromResource();

        int total = Files.readAllLines(filePath).stream()
                .map(Card::new).mapToInt(Card::getScore).sum();

        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("result: " + total);
        System.out.println("time: "+ timeElapsed);
    }
}