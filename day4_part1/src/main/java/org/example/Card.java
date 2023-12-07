package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Card {
    private final static Pattern processor = Pattern.compile("(Card (?<cardId>(\\d+)): (?<numbers>(\\d+ )*)\\| (?<winning>(\\d+( )*)*))");
    private int score = 0;
    private final ArrayList<Integer> numbers = new ArrayList<>();
    private final ArrayList<Integer> winningNumbers = new ArrayList<>();
    private final ArrayList<ArrayList<Integer>> matches = new ArrayList<>();
    public Card (String line) {
        String s = line.replaceAll(" ( )*", " ");
        Matcher match = processor.matcher(s);
        if (match.find()) {
            Arrays.stream(match.group("numbers").split(" "))
                    .toList()
                    .forEach(digit -> this.numbers.add(Integer.parseInt(digit)));

            Arrays.stream(match.group("winning").split(" "))
                    .toList()
                    .forEach(digit -> this.winningNumbers.add(Integer.parseInt(digit)));
            this.setNumberOfMatchingNumbers();
            this.setScore();
        } else {
            throw new Error("invalid Card");
        }
    }

    private void setNumberOfMatchingNumbers() {
        ArrayList<Integer> consecutiveMatches = new ArrayList<>();
        int previousIndex = -1;
        int countConsecutiveMatches = 0;
        int maxConsecutiveMatches = 3;

        for (Integer winning : this.winningNumbers) {
            int numberFoundAt = this.numbers.indexOf(winning);

            if (numberFoundAt != -1) {
                if (previousIndex == -1) {
                    countConsecutiveMatches++;
                    consecutiveMatches.add(winning);
                } else if (previousIndex + 1 == numberFoundAt && countConsecutiveMatches <= maxConsecutiveMatches) {
                    countConsecutiveMatches++;
                    consecutiveMatches.add(winning);
                } else {
                    countConsecutiveMatches = 0;
                    this.matches.add(consecutiveMatches);
                    consecutiveMatches = new ArrayList<>();
                    consecutiveMatches.add(winning);
                }
                previousIndex = numberFoundAt;
            }
        }
        if (!consecutiveMatches.isEmpty()) {
            this.matches.add(consecutiveMatches);
        }
    }

    public int getScore() {
        return this.score;
    }
    private void setScore() {
        matches.forEach(consecutiveMatches -> {
            for (int i = 0; i < consecutiveMatches.size(); i++) {
                if (i == 0 && this.score == 0) {
                    this.score += 1;
                } else {
                    this.score *= 2;
                }

            }
        });
    }

//    public void print() {
//        System.out.println();
//        System.out.println("cardId: " + this.id);
//        System.out.println("numbers: " + this.numbers);
//        System.out.println("winning numbers: " + this.winningNumbers);
//        System.out.println("matches: " + this.matches);
//        System.out.println("score: " + this.score);
//        System.out.println();
//    }
}
