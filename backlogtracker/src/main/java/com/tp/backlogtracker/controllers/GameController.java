package com.tp.backlogtracker.controllers;

import com.tp.backlogtracker.models.Game;
import com.tp.backlogtracker.services.BacklogService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Game> getGamesByUserID(@PathVariable Integer userID) {
        return service.getGamesByUserID(userID);
    }

    @PostMapping("/add")
    public ResponseEntity addGame(@RequestBody Game game) {
        Game toReturn = null;

        return ResponseEntity.ok(toReturn);
    }
}
