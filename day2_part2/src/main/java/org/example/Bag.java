package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Bag {
    private static Pattern processor = Pattern.compile("(?<blue>(\\d+) blue)|(?<red>(\\d+) red)|(?<green>(\\d+) green)");

    public Bag() {}

    public int processResults(String line) {
        HashMap<String, Integer> highestValues = new HashMap<String, Integer>(){{
            put("blue", 0);
            put("red", 0);
            put("green", 0);
        }};

        Stream<MatchResult> matches = processor.matcher(line).results();
        matches.forEach(match -> {
            String[] result = match.group().split(" ");
            int value = Integer.parseInt(result[0]);
            String key = result[1];

            highestValues.merge(key, value, (current, newValue) -> {
                if (current > newValue) {
                    return current;
                } else {
                    return newValue;
                }
            });
        });

        int res = highestValues.get("blue") * highestValues.get("red") * highestValues.get("green");
        return res;
    }
}
