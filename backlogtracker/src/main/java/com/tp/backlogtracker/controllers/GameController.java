package com.tp.backlogtracker.controllers;

import com.tp.backlogtracker.exceptions.InvalidUserIDException;
import com.tp.backlogtracker.exceptions.NoGamesFoundException;
import com.tp.backlogtracker.models.Game;
import com.tp.backlogtracker.models.User;
import com.tp.backlogtracker.services.BacklogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GameController {

    @Autowired
    BacklogService service;

    @GetMapping("/games")
    public List<Game> getAllGames() {
        return service.getAllGames();
    }

    @GetMapping("/games/{userID}")
    public ResponseEntity getGamesByUserID(@PathVariable Integer userID) {
        List<Game> toReturn = null;
        try {
            toReturn = service.getGamesByUserID(userID);
        } catch (NoGamesFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

        return ResponseEntity.ok(toReturn);

    }

    @GetMapping("/user/{userID}")
    public ResponseEntity getUserByID(@PathVariable Integer userID) {
        User toReturn = null;
        try {
            toReturn = service.getUserByID(userID);
        } catch (NoGamesFoundException | InvalidUserIDException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        return ResponseEntity.ok(toReturn);
    }
}
