package com.tp.backlogtracker.persistence;

import com.tp.backlogtracker.models.Game;

import java.util.List;

public interface BacklogDao {
    int addGame(Game game);

    List<Game> getAllGames();
}
