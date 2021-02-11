package com.tp.backlogtracker.controllers;

import com.tp.backlogtracker.exceptions.InvalidUserIDException;
import com.tp.backlogtracker.exceptions.NoGamesFoundException;
import com.tp.backlogtracker.models.Game;
import com.tp.backlogtracker.models.User;
import com.tp.backlogtracker.services.BacklogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class GameController {

    @Autowired
    BacklogService service;

    @GetMapping("/pick/{genre}")
    public ResponseEntity getBacklogGameInGenre(@RequestBody User user, @PathVariable String genre) {
        Game toReturn = null;
        try {
            toReturn = service.getLeastPlayedGameInGenre(user.getUserID(), genre);
        } catch (NoGamesFoundException | InvalidUserIDException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        return ResponseEntity.ok(toReturn);
    }
}