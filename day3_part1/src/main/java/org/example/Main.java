package org.example;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    private Path getFileFromResource(String fileName) throws URISyntaxException {
        URL url = getClass().getClassLoader().getResource(fileName);
        return Paths.get(url.toURI());
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        long start = System.currentTimeMillis();
        Path filePath = new Main().getFileFromResource("input.txt");

        Schematic schematic = new Schematic();

        Files.readAllLines(filePath)
                .forEach(line -> {
                    schematic.addRow(line);
                });

        schematic.printSchema();
        char[] specials = "@=#/%+-$&*".toCharArray();
        Integer res = schematic.process(specials);

        System.out.println(res);
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("time: "+ timeElapsed);
    }
}