package com.tp.hangman.controllers;

import com.tp.hangman.models.HangmanGame;
import com.tp.hangman.models.HangmanViewModel;
import com.tp.hangman.services.HangmanService;
import com.tp.hangman.services.InvalidGameIdException;
import com.tp.hangman.services.InvalidGuessException;
import com.tp.hangman.services.NullGuessException;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//this will provide two methods
//getGameById (should show the word with unguessed letters hidden and all guessed letters)
//guessLetter (should take a game and a letter to guess for that game)
@RestController
public class HangmanController {

    //autowired will automatically bring in any
    // "bean" which is defined as a "component"
    // several annotations derive from component
    // including @Service and @Repository
    // but we can also do @Component
    @Autowired
    HangmanService service;

    // CRUD
    // Create
    //      POST - inserting new data into a system
    //          will typically make use of the body of
    //          the request instead of the URL
    // Read (read one and read all)
    //      GET - retrieving data from a system
    // Update
    //      PUT - editing data in the system
    // Delete
    //      DELETE - removing data from a system

    @GetMapping("/game")
    public List<HangmanViewModel> getAllGames() {
        return service.getAllGames();
    }

    @GetMapping( "/game/{gameId}" )
    public HangmanViewModel getGameById(@PathVariable Integer gameId){
        return service.getGameById( gameId );
    }

/*    @GetMapping("/game/{gameId}/guess/{guess}")
    public ResponseEntity guessLetter(@PathVariable String guess, @PathVariable Integer gameId )
            throws NullGuessException, InvalidGuessException, InvalidGameIdException {

        HangmanViewModel toReturn = null;

        try {
            toReturn = service.makeGuess(gameId, guess);
        } catch(NullGuessException | InvalidGuessException | InvalidGameIdException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

        return ResponseEntity.ok(toReturn);
    }*/

    @PostMapping("/guess")
    public ResponseEntity guessLetter(@RequestBody GuessRequest request) {
        HangmanViewModel toReturn = null;

        try {
            toReturn = service.makeGuess(request.getGameID(), request.getGuess());

        } catch(NullGuessException | InvalidGuessException | InvalidGameIdException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

        return ResponseEntity.ok(toReturn);
    }

    @PostMapping("/begin")
    public HangmanViewModel startGame() {
        HangmanViewModel game = service.startGame();

        return game;
    }

}
