package org.example;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Schematic {

    private final ArrayList<String> rows;
    private final ArrayList<Number> numbers;

    public Schematic(){
        this.rows = new ArrayList<>();
        this.numbers = new ArrayList<>();
    }

    public void log() {
        System.out.println("rows max: " + this.rows.size());
    }
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
                } else if (n instanceof Number && Character.isDigit(c)) {
                    n.pushChar(c, columnPosition);
                } else if (n instanceof Number){
                    this.numbers.add(n);
                    n = null;
                }
                columnPosition++;
            };
            if (n instanceof Number) {
                this.numbers.add(n);
                n = null;
            }
        });
        System.out.println("numbersSize: " + this.numbers.size());
        return this.numbers.stream().filter(number -> number instanceof Number && number.isAdjacent(this, specialChars)).collect(Collectors.summingInt(Number::toNumber));
    }
    public void printSchema() {
        this.rows.forEach(row -> {
            System.out.println(String.valueOf(row));
        });
    }
}
