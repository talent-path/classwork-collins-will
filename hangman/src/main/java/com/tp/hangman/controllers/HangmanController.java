package com.tp.hangman.controllers;

import com.tp.hangman.models.HangmanGame;
import com.tp.hangman.models.HangmanViewModel;
import com.tp.hangman.services.HangmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping( "/game/{gameId}" )
    public HangmanViewModel getGameById(@PathVariable Integer gameId){
        return service.getGameById( gameId );
    }

    @GetMapping("/game/{gameId}/guess/{guess}")
    public HangmanViewModel guessLetter( @PathVariable String guess, @PathVariable Integer gameId ){
        return service.makeGuess( gameId, guess );
    }


}
