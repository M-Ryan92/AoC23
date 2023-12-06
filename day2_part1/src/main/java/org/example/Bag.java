package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Bag {
    private HashMap<String, Integer> mapValue;
    private static Pattern processor = Pattern.compile("(?<blue>(\\d+) blue)|(?<red>(\\d+) red)|(?<green>(\\d+) green)");
    private static Pattern gameNumberProcessor = Pattern.compile("Game (?<id>(\\d+))");

    public Bag(int red, int green, int blue) {
        this.mapValue = new HashMap<>() {{
            put("blue", blue);
            put("green", green);
            put("red", red);
        }};
    }

    private int getGameNumber (String line) {
        Matcher gameIdMatch = gameNumberProcessor.matcher(line);
        if (gameIdMatch.find()){
            String s = gameIdMatch.group("id");
            return Integer.parseInt(s);
        }
        return 0;
    }

    public int processResults(String line) {
        HashMap<String, Integer> lowestValues = new HashMap<String, Integer>(this.mapValue);

        AtomicBoolean valid = new AtomicBoolean(true);
        Integer gameNumber = getGameNumber(line);

        Stream<MatchResult> matches = processor.matcher(line).results();
        matches.forEach(match -> {
            String[] result = match.group().split(" ");
            int value = Integer.parseInt(result[0]);
            String key = result[1];

            if (this.mapValue.get(key) < value) {
                valid.set(false);
            }
        });

        if (valid.get() == true) {
            return gameNumber;
        } else {
            return 0;
        }
    }
}
