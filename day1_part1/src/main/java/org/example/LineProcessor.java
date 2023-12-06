package org.example;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class LineProcessor {
    private final char[] characters;

    private final Integer size;
    private Integer pointerLeft = -1;
    private Integer pointerRight = -1;

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

    private void getLeft(int iterator) {
        Optional<Character> characterLeft = getCharacter(this.pointerLeft, iterator);

        if (characterLeft.isPresent()) {
            char l = characterLeft.get();
            if (Character.isDigit(l)) {
                this.pointerLeft = Integer.parseInt(String.valueOf(l)) * 10;
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
        return res;
    }
}
