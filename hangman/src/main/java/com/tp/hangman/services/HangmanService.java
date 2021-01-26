package com.tp.hangman.services;

import com.tp.hangman.models.HangmanGame;
import com.tp.hangman.models.HangmanViewModel;
import com.tp.hangman.persistence.HangmanDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//handles the logic for the game
@Component
public class HangmanService {

    @Autowired
    HangmanDao dao;

    public HangmanViewModel getGameById(Integer gameId) {
        HangmanGame game = dao.getGameById( gameId );
        return convertModel( game );
    }


    public HangmanViewModel makeGuess(Integer gameId, String guess) {
        if( guess.length() != 1){
            //TODO: make and throw a custom exception here
            return null;
        }

        if( gameId == null ){
            //TODO: make and throw a custom exception here
            return null;
        }

        HangmanGame game = dao.getGameById(gameId);

        //we'll assume here that the dao gives us back a null
        //if there's no matching game
        if( game == null) {
            return null;
        }

        if (game.getWrongGuesses() >= 5) {
            return null;
        }

        boolean allLettersGuessed = true;

        for (int i = 0; i < game.getHiddenWord().length(); i++) {
            if (!game.getGuessedLetters().contains(game.getHiddenWord().charAt(i))) {
                allLettersGuessed = false;
                break;
            }
        }

        if (allLettersGuessed) {
            return null;
        }

        if (!game.getGuessedLetters().contains(guess.charAt(0))) {
            game.getGuessedLetters().add(guess.charAt(0));

            boolean contains = false;

            for (int i = 0; i < game.getHiddenWord().length(); i++) {
                if (game.getHiddenWord().charAt(i) == guess.charAt(0)) {
                    contains = true;
                    break;
                }
            }

            if (!contains) {
                game.incrementWrongGuesses();
            }

            if (game.getWrongGuesses() >= 5) {
                return null;
            }
        }

        return convertModel(game);

    }


    private HangmanViewModel convertModel(HangmanGame game) {
        //TODO: generate the string with all the letters hidden that have not been guessed
        //and build the view model object (using the setters)

        String partialWord = "";

        for (int i = 0; i < game.getHiddenWord().length(); i++) {
            if (game.getGuessedLetters().contains(game.getHiddenWord().charAt(i))) {
                partialWord += game.getHiddenWord().charAt(i);
            } else {
                partialWord += '_';
            }
        }

        HangmanViewModel toReturn = new HangmanViewModel();
        toReturn.setPartialWord( partialWord );
        toReturn.setGuessedLetters( game.getGuessedLetters() );
        toReturn.setWrongGuesses(game.getWrongGuesses());

        return toReturn;
    }
}
