package com.tp.connectfour.controllers;

import com.tp.connectfour.exceptions.GameOverException;
import com.tp.connectfour.exceptions.InvalidGameIDException;
import com.tp.connectfour.exceptions.InvalidMoveException;
import com.tp.connectfour.exceptions.NullGameIDException;
import com.tp.connectfour.models.ConnectFourViewModel;
import com.tp.connectfour.services.ConnectFourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ConnectFourController {

    @Autowired
    ConnectFourService service;

    //CRUD
    //Create
    //      POST - inserting new data into a system
    //              will typically make use of the "body"
    //              of the request to send data rather than the url
    //Read (read one and read all)
    //      GET - retriveing data from a system
    //Update
    //      PUT - editing data already in a system
    //Delete
    //      DELETE - removing data from a system

    @GetMapping("/game")
    public List<ConnectFourViewModel> getAllGames(){
        return service.getAllGames();
    }

    @GetMapping( "/game/{gameID}" )
    public ConnectFourViewModel  getGameByID(@PathVariable Integer gameID) throws NullGameIDException {
        ConnectFourViewModel toReturn = null;

        try{
            toReturn = service.getGameByID(gameID);
        } catch (NullGameIDException | InvalidGameIDException ex) {

        }

        return toReturn;
    }

//    @GetMapping("/game/{gameId}/guess/{guess}")
//    public ResponseEntity guessLetter( @PathVariable String guess, @PathVariable Integer gameId )
//    {
//
//        HangmanViewModel toReturn = null;
//
//        try{
//            toReturn = service.makeGuess( gameId, guess );
//        } catch( NullGuessException | InvalidGuessException | InvalidGameIdException ex ){
//
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
//        }
//
//        return ResponseEntity.ok(toReturn);
//
//    }

    @PostMapping("/move")
    public ResponseEntity makeMove(@RequestBody MoveRequest move)
    {
        ConnectFourViewModel toReturn = null;

        try{
            toReturn = service.makeMove( move.getGameID(), move.getMove() );
        } catch(InvalidMoveException | NullGameIDException | InvalidGameIDException | GameOverException ex){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

        return ResponseEntity.ok(toReturn);

    }

    @PostMapping("/begin")
    public ConnectFourViewModel startGame(){
        ConnectFourViewModel game = service.startGame();

        return game;
    }

    @DeleteMapping("/delete/{gameID}")
    public String deleteGame( @PathVariable Integer gameID ){
        try {
            service.deleteGame( gameID );
            return "Game " + gameID + " successfully deleted.";
        } catch (InvalidGameIDException e) {
            return e.getMessage();
        }
    }

    @PutMapping("/clear/{gameID}")
    public String clearBoard( @PathVariable Integer gameID)
    {
        try {
            service.clearBoard( gameID );
            return "Game " + gameID + " successfully cleared.";

        } catch (InvalidGameIDException | NullGameIDException e)
        {
            return e.getMessage();
        }
    }

}
