package org.example;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    private Path getFileFromResource() throws URISyntaxException {
        URL url = getClass().getClassLoader().getResource("input.txt");
        return Paths.get(url.toURI());
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        long start = System.currentTimeMillis();
        Path filePath = new Main().getFileFromResource();

        HashMap<Integer, Card> cardMap = new HashMap<>();
        Files.readAllLines(filePath).stream().map(Card::new).forEach(card -> cardMap.put(card.getId(), card));

        cardMap.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(set -> {
            Card card = set.getValue();
            int id = card.getId();
            int copies = card.getCopies();
            Integer hitSize = card.getHitSize();
            System.out.println("h: " + hitSize + " id: " + id + " copies: " + copies);

            int begin = id + 1;
            int end = begin + hitSize;

            for (int i = begin; i < Math.min(end, cardMap.size()) ; i++) {
                    Card reward = cardMap.get(i);
                    reward.addCopies(copies);
                    cardMap.replace(set.getKey(), reward);
            }
        });

//        cardMap.values().forEach(card -> card.print());
        int numberOfCards = cardMap.values().stream().mapToInt(Card::getCopies).sum();

        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("result: " + numberOfCards);
        System.out.println("time: "+ timeElapsed);
    }
}
