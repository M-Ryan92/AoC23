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
        if (url.toURI() != null) {
            return Paths.get(url.toURI());
        }

        return null;
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        long start = System.currentTimeMillis();
        Path filePath = new Main().getFileFromResource();

        Schematic schematic = new Schematic();

        Files.readAllLines(filePath)
                .forEach(schematic::addRow);

        schematic.printSchema();
        char[] specials = "@=#/%+-$&*".toCharArray();
        Integer res = schematic.process(specials);

        System.out.println("result: " + res);
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("time: "+ timeElapsed);
    }
}