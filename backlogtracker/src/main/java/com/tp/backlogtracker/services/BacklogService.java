package com.tp.backlogtracker.services;

import com.tp.backlogtracker.exceptions.InvalidUserIDException;
import com.tp.backlogtracker.exceptions.NoGamesFoundException;
import com.tp.backlogtracker.models.Game;
import com.tp.backlogtracker.models.User;
import com.tp.backlogtracker.persistence.GameDao;
import com.tp.backlogtracker.persistence.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
public class BacklogService {

    @Autowired
    GameDao gameDao;

    @Autowired
    UserDao userDao;

    public List<Game> getGamesByUserID(Integer userID) throws NoGamesFoundException, InvalidUserIDException {
        return gameDao.getGamesByUserID(userID);
    }

    public User getUserByID(Integer userID) throws NoGamesFoundException, InvalidUserIDException {
        User partialUser = userDao.getUserByID(userID);
        List<Game> userGames = gameDao.getGamesByUserID(userID);
        partialUser.setLibrary(userGames);
        return partialUser;
    }

    // sort methods: return entire library sorted
    // get methods: return only games that meet criteria

    public User sortUserGamesByGenre(Integer userID) throws NoGamesFoundException, InvalidUserIDException {
        User user = getUserByID(userID);
        List<Game> games = user.getLibrary();
        Comparator<Game> gameComparator = Comparator.comparing(Game::getGenre);
        Collections.sort(games, gameComparator);
        user.setLibrary(games);
        return user;
    }

    public User getUserGamesByGenre(Integer userID, String genre) throws NoGamesFoundException, InvalidUserIDException {
        User user = getUserByID(userID);
        List<Game> genreGames = new ArrayList<>();

        for (Game game : user.getLibrary()) {
            if (game.getGenre().equals(genre.substring(0,1).toUpperCase()+genre.substring(1).toLowerCase())) {
                genreGames.add(game);
            }
        }

        if (genreGames.size() == 0) {
            throw new NoGamesFoundException("No " + genre.substring(0,1).toUpperCase()+genre.substring(1).toLowerCase()
                + " games found in user's library");
        }

        user.setLibrary(genreGames);
        return user;
    }

    public User sortUserGamesByPlayTime(Integer userID) throws NoGamesFoundException, InvalidUserIDException {
        User user = getUserByID(userID);
        List<Game> games = user.getLibrary();
        Comparator<Game> gameComparator = Comparator.comparing(Game::getHoursPlayed);
        Collections.sort(games, gameComparator);
        user.setLibrary(games);
        return user;
    }
}
