package com.tp.hangman.services;

import com.tp.hangman.models.HangmanGame;
import com.tp.hangman.models.HangmanViewModel;
import com.tp.hangman.persistence.HangmanDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//handles the logic for the game
@Component
public class HangmanService {

    @Autowired
    HangmanDao dao;

    String[] possibleWords = {}

    public HangmanViewModel startGame() {
        // 1. pick a word
        // 2. Insert game into dao
        // 3. Get back the ID from the dao
        // 4. Return a viewmodel from that game


    }

    public List<HangmanViewModel> getAllGames() {
        List<HangmanGame> allGames = dao.getAllGames();
        List<HangmanViewModel> converted = new ArrayList<>();

        for (HangmanGame game : allGames) {
            converted.add(convertModel(game));
        }

        return converted;
    }

    public HangmanViewModel getGameById(Integer gameId) {
        HangmanGame game = dao.getGameById( gameId );
        return convertModel( game );
    }

    public HangmanViewModel makeGuess(Integer gameId, String guess) throws NullGuessException, InvalidGuessException, InvalidGameIdException {

        if (guess == null) {
            throw new NullGuessException("Tried to make guess on game ID " + gameId + " with a null guess.");
        }

        if( guess.length() != 1){
            throw new InvalidGuessException("A guess of " + guess + " is too long.");
        }

        if( gameId == null ){
            throw new InvalidGameIdException("Missing game id");
        }

        HangmanGame game = dao.getGameById(gameId);

        //we'll assume here that the dao gives us back a null
        //if there's no matching game
        if( game == null) {
            throw new InvalidGameIdException("Could not find game with id " + gameId);
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

        dao.updateGame(game);

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
