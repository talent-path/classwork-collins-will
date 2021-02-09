package com.tp.backlogtracker.services;

import com.tp.backlogtracker.models.Game;
import com.tp.backlogtracker.persistence.GameDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BacklogService {

    @Autowired
    GameDao dao;

    public Game addGame(Game game) {
        return game;
    }

    public List<Game> getAllGames() {
        return dao.getAllGames();
    }
}
