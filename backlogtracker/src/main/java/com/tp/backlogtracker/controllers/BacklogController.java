package com.tp.backlogtracker.controllers;

import com.tp.backlogtracker.models.Game;
import com.tp.backlogtracker.services.BacklogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BacklogController {

    @Autowired
    BacklogService service;

    @PostMapping("/add")
    public ResponseEntity addBook(@RequestBody Game game) {
        Game toReturn = null;
        
        return ResponseEntity.ok(toReturn);
    }
}
