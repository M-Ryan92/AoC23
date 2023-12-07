package org.example;

import java.util.*;

public class Card {
//    (?<drawnNumbers>(\d+( )*)*)$
//    (?<drawnNumbers>(( )*\d+))*
    private final HashSet<Integer> cardNumbers = new HashSet<>();
    private final HashSet<Integer> hits = new HashSet<>();
    private final Integer hitSize;
    private final HashSet<Integer> drawnNumbers = new HashSet<>();
//
    private final int id;
    private int copies = 1;

    public Card (String line) {
        String[] result = line.split(":");

        this.id = Integer.parseInt(result[0].replaceAll("Card( )*", ""));
        String[] numbers = result[1].split("\\|");

        Arrays.stream(numbers[0].split(" ")).toList().forEach(digit -> {
            if (!Objects.equals(digit, "")) {
                this.cardNumbers.add(Integer.parseInt(digit));
            }
        });

        Arrays.stream(numbers[1].split(" ")).toList().forEach(digit -> {
            if (!Objects.equals(digit, "")) {
                this.drawnNumbers.add(Integer.parseInt(digit));
            }
        });

        this.hits.addAll(this.cardNumbers);
        this.hits.retainAll(this.drawnNumbers);
        this.hitSize = this.hits.size();
    }

    public void print() {
            System.out.println();
            System.out.println("cardId: " + this.id);
            System.out.println("hits: " + this.hits);
            System.out.println("hitSize: " + this.hitSize);
            System.out.println("cardNumbers: " + this.cardNumbers);
            System.out.println("drawn numbers: " + this.drawnNumbers);
            System.out.println("copies: " + this.copies);
            System.out.println();
    }

    public Integer getHitSize() {
        return this.hitSize;
    }
    public int getId() {
        return this.id;
    }

    public int getCopies() {
        return this.copies;
    }

    public void addCopies(int increment) {
        this.copies += increment;
    }
}
