package org.example;


import java.util.*;

public class LineProcessor {
    private final char[] characters;

    private final Integer size;
    private Integer pointerLeft = -1;
    private final LinkedList<Character> stackLeft = new LinkedList<>();
    private Integer pointerRight = -1;
    private final LinkedList<Character> stackRight = new LinkedList<>();

    private static final Map<String, Integer> threeLetterNumbers = Map.of("one", 1, "two", 2, "six", 6);
    private static final Map<String, Integer> fourLetterNumbers = Map.of("four", 4, "five", 5, "nine", 9);
    private static final Map<String, Integer> fiveLetterNumbers = Map.of("three", 3, "seven", 7, "eight", 8);


    public LineProcessor(String line){
        this.characters = line.toCharArray();
        this.size = line.toCharArray().length - 1;
    }

    private Optional<Character> getCharacter(int pointer, int iteration) {
        if (pointer != -1) {
            return Optional.empty();
        }

        return Optional.of(this.characters[iteration]);
    }


    private String stackToString(LinkedList<Character> stack, int number) {
        return stackToString(stack, number, false);
    }

    private String stackToString(LinkedList<Character> stack, int number, boolean stripAtFront){
        StringBuilder stringBuilder = new StringBuilder();
        int size = stack.size();
        int start = 0;
        int end = size;

        if (!stripAtFront && size > number) {
            end = size - (size - number);
        }

        if (stripAtFront && size > number) {
            start = size - number;
        }

        for (int i = start; i < end; i++) {
            if (i < size) {
                stringBuilder.append(stack.get(i));
            }
        }
         return stringBuilder.toString();
    }

    private void getLeft(int iterator) {
        Optional<Character> characterLeft = getCharacter(this.pointerLeft, iterator);

        if (characterLeft.isPresent()) {
            char l = characterLeft.get();
            if (Character.isDigit(l)) {
                this.pointerLeft = Integer.parseInt(String.valueOf(l)) * 10;
            }
            this.stackLeft.add(l);
            if (this.stackLeft.size() > 5) {
                this.stackLeft.remove(0);
            }

            String fiveLetterKey = this.stackToString(this.stackLeft, 5, true);
            String fourLetterKey = this.stackToString(this.stackLeft, 4, true);
            String threeLetterKey = this.stackToString(this.stackLeft, 3, true);

            if (fiveLetterNumbers.containsKey(fiveLetterKey)){
                int value = fiveLetterNumbers.get(fiveLetterKey);
                this.pointerLeft = value * 10;
            } else if (fourLetterNumbers.containsKey(fourLetterKey)) {
                int value = fourLetterNumbers.get(fourLetterKey);
                this.pointerLeft = value * 10;
            } else if (threeLetterNumbers.containsKey(threeLetterKey)) {
                int value = threeLetterNumbers.get(threeLetterKey);
                this.pointerLeft = value * 10;
            }
        }

    }
    private void getRight(int iterator) {
        Optional<Character> characterRight = getCharacter(this.pointerRight, this.size - iterator);

        if (characterRight.isPresent()) {
            char r = characterRight.get();
            if (Character.isDigit(r)) {
                this.pointerRight = Integer.parseInt(String.valueOf(r));
            }
            this.stackRight.addFirst(r);

            if (this.stackRight.size() > 5) {
                this.stackRight.removeLast();
            }

            String fiveLetterKey = this.stackToString(this.stackRight, 5);
            String fourLetterKey = this.stackToString(this.stackRight, 4);
            String threeLetterKey = this.stackToString(this.stackRight, 3);

            if (fiveLetterNumbers.containsKey(fiveLetterKey)){
                this.pointerRight = fiveLetterNumbers.get(fiveLetterKey);
            } else if (fourLetterNumbers.containsKey(fourLetterKey)) {
                this.pointerRight = fourLetterNumbers.get(fourLetterKey);
            } else if (threeLetterNumbers.containsKey(threeLetterKey)) {
                this.pointerRight = threeLetterNumbers.get(threeLetterKey);
            }
        }

    }

    public int calculate(){
        int i = 0;
        while (pointerLeft == -1 || this.pointerRight == -1) {
            getLeft(i);
            getRight(i);
            i++;
        }

        int res = this.pointerLeft + this.pointerRight;
        System.out.println("res:" + res);
        return res;
    }
}
