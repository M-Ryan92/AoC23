package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Number {
    private boolean isSingleDigit = true;
    private boolean isLast = false;
    private boolean isFirst = false;
    private boolean isOnBottom = false;
    private boolean isOnTop = false;

    private int row = -1;
    private int start = -1;
    private int end = -1;
    private ArrayList<Character> characters;
    private int maxColumnSize;
    private int maxRowSize;

    public Number(int maxColumnSize, int maxRowSize, int currentRow) {
        this.maxColumnSize = maxColumnSize;
        this.maxRowSize = maxRowSize;
        this.characters = new ArrayList<Character>();
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
        for (int i = 0; i < this.characters.size(); i++) {
            stringBuilder.append(this.characters.get(i));
        }
        return Integer.parseInt(stringBuilder.toString());
    }

    public void pushChar(char c, int pos) {
        if (characters.isEmpty() == true) {
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


    public void log() {
        System.out.println("first: " + this.isFirst);
        System.out.println("last: " + this.isLast);
        System.out.println("onTop: " + this.isOnTop);
        System.out.println("onBottom: " + this.isOnBottom);
        System.out.println("start: " + this.start);
        System.out.println("end: " + this.end);
        System.out.println("row: " + this.row);
        System.out.println("maxRowSize: " + this.maxRowSize);
        System.out.println("maxColumnSize: " + this.maxColumnSize);
        System.out.println("chars: " + toNumber());


        System.out.println();
    }

    public ArrayList<Integer> validationFields(boolean onSameRow) {
        ArrayList<Integer> fields = new ArrayList<Integer>();
        if (this.isFirst == false) {
            fields.add(this.start -1);
        }

        if (onSameRow == false) {
            for (int i = start; i <= end ; i++) {
                fields.add(i);
            }
        }

        if (this.isLast == false && this.end + 1 < this.maxColumnSize) {
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
            }
        }

        return isValid;
    }
    public boolean isAdjacent(Schematic schematic, char[] specialCharacters) {
        AtomicBoolean isAdjacent = new AtomicBoolean(false);
        ArrayList<Integer> fieldsToCheckOnSameRow = this.validationFields(true);
        ArrayList<Integer> fieldsToCheckAdjacentRow = this.validationFields(false);
        String currentRowChars = schematic.getRow(this.row);

        fieldsToCheckOnSameRow.forEach(column -> {
            Character value = currentRowChars.charAt(column);
            if (isAdjacent.get() == false) {
                boolean adjacent = validateAdjacentValue(value, specialCharacters);
                isAdjacent.set(adjacent);
            }
        });

        if (this.isOnTop == false && isAdjacent.get() == false) {
            String topRowChars = schematic.getRow(this.row - 1);

            fieldsToCheckAdjacentRow.forEach(column -> {
                if (isAdjacent.get() == false) {
                    boolean adjacentTop = validateAdjacentValue(topRowChars.charAt(column), specialCharacters);
                    isAdjacent.set(adjacentTop);
                }
            });
        }

        if (this.isOnBottom == false && isAdjacent.get() == false) {
            String bottomRowChars = schematic.getRow(this.row + 1);

            fieldsToCheckAdjacentRow.forEach(column -> {
                if (isAdjacent.get() == false) {
                    boolean adjacentBottom = validateAdjacentValue(bottomRowChars.charAt(column), specialCharacters);
                    isAdjacent.set(adjacentBottom);
                }
            });
        }

        return isAdjacent.get();
    }
}
