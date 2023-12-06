package org.example;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Number {
    private boolean isSingleDigit = true;
    private boolean isLast = false;
    private boolean isFirst = false;
    private boolean isOnBottom = false;
    private boolean isOnTop = false;

    private final int row;
    private int start = -1;
    private int end = -1;
    private final ArrayList<Character> characters = new ArrayList<>();
    private final int maxColumnSize;

    private int adjacentX = -1;
    private int adjacentY = -1;

    public Number(int maxColumnSize, int maxRowSize, int currentRow) {
        this.maxColumnSize = maxColumnSize;
        this.row = currentRow;

        if (currentRow == 0) {
            isOnTop = true;
        }
        if ( currentRow == maxRowSize) {
            isOnBottom = true;
        }
    }

    public int toNumber() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Character character : this.characters) {
            stringBuilder.append(character);
        }
        return Integer.parseInt(stringBuilder.toString());
    }

    public void pushChar(char c, int pos) {
        if (characters.isEmpty()) {
            this.start = pos;
        }
        this.end = pos;

        characters.add(c);
        if (this.isSingleDigit && characters.size() > 1) {
            this.isSingleDigit = false;
        }

        if (pos == 0) {
            isFirst = true;
        }
        if (pos == this.maxColumnSize) {
            this.isLast = true;
        }

    }


//    public void log() {
//        System.out.println("first: " + this.isFirst);
//        System.out.println("last: " + this.isLast);
//        System.out.println("onTop: " + this.isOnTop);
//        System.out.println("onBottom: " + this.isOnBottom);
//        System.out.println("start: " + this.start);
//        System.out.println("end: " + this.end);
//        System.out.println("row: " + this.row);
//        System.out.println("maxRowSize: " + this.maxRowSize);
//        System.out.println("maxColumnSize: " + this.maxColumnSize);
//        System.out.println("chars: " + toNumber());
//        System.out.println("adjacent?: " + getAdjacent());
//
//        System.out.println();
//    }

    public ArrayList<Integer> validationFields(boolean onSameRow) {
        ArrayList<Integer> fields = new ArrayList<>();
        if (!this.isFirst) {
            fields.add(this.start -1);
        }

        if (!onSameRow) {
            for (int i = start; i <= end ; i++) {
                fields.add(i);
            }
        }

        if (!this.isLast && this.end + 1 < this.maxColumnSize) {
            fields.add(this.end + 1);
        }
        return fields;
    }

    private boolean validateAdjacentValue(char c, char[] specialCharacters) {
        boolean isValid = false;

        for (char specialCharacter : specialCharacters) {
            boolean matched = c == specialCharacter;
            if (matched) {
                isValid = true;
                break;
            }
        }

        return isValid;
    }

    public String getAdjacent() {
        return "X" + this.adjacentX + "_Y" + this.adjacentY;
    }
    public boolean isAdjacent(Schematic schematic, char[] specialCharacters) {
        AtomicBoolean isAdjacent = new AtomicBoolean(false);
        ArrayList<Integer> fieldsToCheckOnSameRow = this.validationFields(true);
        ArrayList<Integer> fieldsToCheckAdjacentRow = this.validationFields(false);
        String currentRowChars = schematic.getRow(this.row);

        fieldsToCheckOnSameRow.forEach(column -> {
            char value = currentRowChars.charAt(column);
            if (!isAdjacent.get()) {
                boolean adjacent = validateAdjacentValue(value, specialCharacters);
                if (adjacent) {
                    this.adjacentX = column;
                    this.adjacentY = this.row;
                }
                isAdjacent.set(adjacent);
            }
        });

        if (!this.isOnTop && !isAdjacent.get()) {
            String topRowChars = schematic.getRow(this.row - 1);

            fieldsToCheckAdjacentRow.forEach(column -> {
                if (!isAdjacent.get()) {
                    boolean adjacentTop = validateAdjacentValue(topRowChars.charAt(column), specialCharacters);
                    if (adjacentTop) {
                        this.adjacentX = column;
                        this.adjacentY = this.row - 1;
                    }
                    isAdjacent.set(adjacentTop);
                }
            });
        }

        if (!this.isOnBottom && !isAdjacent.get()) {
            String bottomRowChars = schematic.getRow(this.row + 1);

            fieldsToCheckAdjacentRow.forEach(column -> {
                if (!isAdjacent.get()) {
                    boolean adjacentBottom = validateAdjacentValue(bottomRowChars.charAt(column), specialCharacters);
                    if (adjacentBottom) {
                        this.adjacentX = column;
                        this.adjacentY = this.row + 1;
                    }
                    isAdjacent.set(adjacentBottom);
                }
            });
        }

        return isAdjacent.get();
    }
}
