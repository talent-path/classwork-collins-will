package com.tp.hangman.persistence;

import com.tp.hangman.models.HangmanGame;

import java.util.List;

//"dao" stands for "data access object"
//this naming is conventional for Java,
//but somewhat unusual for other languages
public interface HangmanDao {
    HangmanGame getGameById(Integer gameId);

    //List<HangmanGame> getGamesByCategory(String category );
}
