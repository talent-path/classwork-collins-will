package com.tp.backlogtracker.services;

import com.tp.backlogtracker.exceptions.InvalidUserIDException;
import com.tp.backlogtracker.exceptions.NoGamesFoundException;
import com.tp.backlogtracker.models.Game;
import com.tp.backlogtracker.models.User;
import com.tp.backlogtracker.persistence.GameDao;
import com.tp.backlogtracker.persistence.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BacklogService {

    @Autowired
    GameDao gameDao;

    @Autowired
    UserDao userDao;

    public Game addGame(Game game) {
        return game;
    }

    public List<Game> getAllGames() {
        return gameDao.getAllGames();
    }

    public List<Game> getGamesByUserID(int userID) throws NoGamesFoundException {
        return gameDao.getGamesByUserID(userID);
    }

    public User getUserByID(int userID) throws NoGamesFoundException, InvalidUserIDException {
        User partialUser = userDao.getUserByID(userID);
        List<Game> userGames = gameDao.getGamesByUserID(userID);
        partialUser.setLibrary(userGames);
        return partialUser;
    }
}
