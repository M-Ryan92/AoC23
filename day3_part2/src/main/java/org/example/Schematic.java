package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Schematic {
    private final ArrayList<String> rows;
    private final ArrayList<Number> numbers;

    public Schematic(){
        this.rows = new ArrayList<>();
        this.numbers = new ArrayList<>();
    }

//    public void log() {
//        System.out.println("rows max: " + this.rows.size());
//    }
    public String getRow(int row) {
        return this.rows.get(row);
    }
    public void addRow(String line) {
        this.rows.add(line);
    }

    public Integer process(char[] specialChars) {
        AtomicInteger index = new AtomicInteger();
        final int maxRows = this.rows.size() - 1;

        this.rows.stream().map(i -> index.getAndIncrement()).forEach(rowNumber -> {
            String line = this.rows.get(rowNumber);
            char[] chars = line.toCharArray();
            int columnPosition = 0;

            Number n = null;
            for (char c : chars){
                if (n == null && Character.isDigit(c)) {
                    n = new Number(chars.length, maxRows, rowNumber);
                    n.pushChar(c, columnPosition);
                } else if (n != null && Character.isDigit(c)) {
                    n.pushChar(c, columnPosition);
                } else if (n != null){
                    this.numbers.add(n);
                    n = null;
                }
                columnPosition++;
            }
            if (n != null) {
                this.numbers.add(n);
            }
        });

        Map<String, List<Number>> groupedNumbers = this.numbers.stream()
                .filter(number -> number != null && number.isAdjacent(this, specialChars))
                .collect(Collectors.groupingBy(Number::getAdjacent));

        return groupedNumbers.values().stream().filter(group -> group.size() > 1)
                .map(group -> group.stream().map(Number::toNumber).reduce((prev, next) -> prev * next).get()).mapToInt(Integer::intValue).sum();
    }
    public void printSchema() {
        this.rows.forEach(System.out::println);
    }
}
