package com.tp.backlogtracker.persistence;

import com.tp.backlogtracker.exceptions.InvalidUserIDException;
import com.tp.backlogtracker.exceptions.NoGamesFoundException;
import com.tp.backlogtracker.models.Game;

import java.util.List;

public interface GameDao {
    List<Game> getGamesByUserID(Integer userID) throws NoGamesFoundException, InvalidUserIDException;
}
