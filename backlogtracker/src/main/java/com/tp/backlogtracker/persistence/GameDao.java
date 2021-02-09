package com.tp.backlogtracker.persistence;

import com.tp.backlogtracker.exceptions.NoGamesFoundException;
import com.tp.backlogtracker.models.Game;

import java.util.List;

public interface GameDao {
    List<Game> getAllGames();
    List<Game> getGamesByUserID(int userID) throws NoGamesFoundException;
}
