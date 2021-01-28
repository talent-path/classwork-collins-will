package com.tp.hangman.models;

import ch.qos.logback.core.util.InvocationGate;

import java.util.ArrayList;
import java.util.List;

public class HangmanGame {
    Integer gameId;
    String hiddenWord;
    Integer wrongGuesses = 0;


    List<Character> guessedLetters;

    //constructor for a new game
    public HangmanGame( Integer gameId, String hiddenWord ){
        this.gameId = gameId;
        this.hiddenWord = hiddenWord;
        guessedLetters = new ArrayList<>();
    }

    //constructor for an existing game (will have more use after we have databases)
    public HangmanGame(Integer gameId, String hiddenWord, List<Character> guessedLetters){
        this.gameId = gameId;
        this.hiddenWord = hiddenWord;
        this.guessedLetters = guessedLetters;
    }

    // copy constructor
    public HangmanGame(HangmanGame that) {
        this.gameId = that.gameId;
        this.hiddenWord = that.hiddenWord;
        this.guessedLetters = new ArrayList<>();

        for (Character toCopy : that.guessedLetters) {
            this.guessedLetters.add(toCopy);
        }

        this.wrongGuesses = that.wrongGuesses;
    }

    public Integer getGameId() {
        return gameId;
    }

    public String getHiddenWord() {
        return hiddenWord;
    }

    public List<Character> getGuessedLetters() {
        return guessedLetters;
    }

    public Integer getWrongGuesses() {
        return this.wrongGuesses;
    }

    public void incrementWrongGuesses() {
        this.wrongGuesses++;
    }
}
