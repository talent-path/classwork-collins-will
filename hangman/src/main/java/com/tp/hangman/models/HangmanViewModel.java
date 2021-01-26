package com.tp.hangman.models;

import java.util.List;

public class HangmanViewModel {
    String partialWord;
    List<Character> guessedLetters;
    Integer wrongGuesses = 0;

    public String getPartialWord() {
        return partialWord;
    }

    public void setPartialWord(String partialWord) {
        this.partialWord = partialWord;
    }

    public List<Character> getGuessedLetters() {
        return guessedLetters;
    }

    public void setGuessedLetters(List<Character> guessedLetters) {
        this.guessedLetters = guessedLetters;
    }

    public Integer getWrongGuesses() {
        return wrongGuesses;
    }

    public void setWrongGuesses(Integer wrongGuesses) {
        this.wrongGuesses = wrongGuesses;
    }
}
