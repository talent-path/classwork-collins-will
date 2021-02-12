package com.tp.backlogtracker.services;

import com.tp.backlogtracker.exceptions.InvalidUserIDException;
import com.tp.backlogtracker.exceptions.NoGamesFoundException;
import com.tp.backlogtracker.models.Game;
import com.tp.backlogtracker.models.User;
import com.tp.backlogtracker.persistence.GameDao;
import com.tp.backlogtracker.persistence.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

@Component
public class BacklogService {

    @Autowired
    GameDao gameDao;

    @Autowired
    UserDao userDao;

    Random rand = new Random();

    public List<Game> getGamesByUserID(Integer userID) throws NoGamesFoundException, InvalidUserIDException {
        return gameDao.getGamesByUserID(userID);
    }

    public User getUserByID(Integer userID) throws NoGamesFoundException, InvalidUserIDException {
        User partialUser = userDao.getUserByID(userID);
        List<Game> userGames = gameDao.getGamesByUserID(userID);
        partialUser.setLibrary(userGames);
        partialUser.setAvgPlayTime(gameDao.getUserAveragePlayTime(userID));
        partialUser.setNumUncompletedGames(gameDao.getNumOfUncompletedGames(userID));
        if (partialUser.getLibrary().size() != 0) {
            partialUser.setPercentCompleted(((double) partialUser.getNumUncompletedGames()) / partialUser.getLibrary().size());
        } else {
            partialUser.setPercentCompleted(0.0);
        }
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
        List<Game> genreGames = gameDao.getUserGamesOfGenre(userID, genre);

        if (genreGames.size() == 0) {
            throw new NoGamesFoundException("No " + genre.substring(0,1).toUpperCase()+genre.substring(1).toLowerCase()
                + " games found in user's library");
        }

        user.setLibrary(genreGames);
        return user;
    }

    public User sortUserGamesByHoursPlayed(Integer userID) throws NoGamesFoundException, InvalidUserIDException {
        User user = getUserByID(userID);
        List<Game> games = user.getLibrary();
        Comparator<Game> gameComparator = Comparator.comparing(Game::getHoursPlayed);
        Collections.sort(games, gameComparator);
        user.setLibrary(games);
        return user;
    }

    public User getUserGamesUnderHoursPlayed(Integer userID, Double hoursPlayed) throws NoGamesFoundException, InvalidUserIDException {
        User user = getUserByID(userID);
        List<Game> playTimeGames = gameDao.getUserGamesUnderHoursPlayed(userID, hoursPlayed);

        if (playTimeGames.size() == 0) {
            throw new NoGamesFoundException("No games found under " + hoursPlayed + " hours played in user's library");
        }
        user.setLibrary(playTimeGames);
        return user;
    }

    public Game getLeastPlayedGameInGenre(Integer userID, String genre) throws NoGamesFoundException, InvalidUserIDException {
        List<Game> leastPlayedGenreGames = gameDao.getLeastPlayedGameInGenre(userID, genre);
        if (leastPlayedGenreGames.size() == 0) {
            throw new NoGamesFoundException("No eligible uncompleted games found owned by user " + userID);
        } else {
            return leastPlayedGenreGames.get(rand.nextInt(leastPlayedGenreGames.size()));
        }
    }

    public String changeCompletedStatus(Integer userID, Integer gameID) throws NoGamesFoundException {
        Game game = gameDao.changeCompletedStatus(userID, gameID);
        String gameStatus = "";
        if (game.isCompleted()) {
            gameStatus = "completed";
        } else {
            gameStatus = "uncompleted";
        }
        return game.getName() + "'s status has been changed to " + gameStatus + " for user " + userID;
    }
}
