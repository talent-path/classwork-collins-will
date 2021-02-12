package com.tp.backlogtracker.persistence;

import com.tp.backlogtracker.exceptions.InvalidUserIDException;
import com.tp.backlogtracker.exceptions.NoGamesFoundException;
import com.tp.backlogtracker.models.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Profile("serviceTesting")
public class GameInMemDao implements GameDao {

    @Autowired
    JdbcTemplate template;

    // game, userID
    Map<Game, Integer> allGames = new HashMap<>();

    public GameInMemDao() {
        allGames = new HashMap<>();
    }

    @Override
    public int addGame(Integer userID, Game game) {
        allGames.put(game, userID);
        return userID;
    }

    @Override
    public List<Game> getGamesByUserID(Integer userID) throws NoGamesFoundException, InvalidUserIDException {
        if (userID == null) {
            throw new InvalidUserIDException("User ID cannot be null");
        }

        List<Game> games = new ArrayList<>();
        for (Game toCheck : allGames.keySet()) {
            if (allGames.get(toCheck) == userID) {
                games.add(toCheck);
            }
        }

        if (games.size() == 0) {
            throw new NoGamesFoundException("No games found owned by user ID " + userID);
        }

        return games;
    }

    @Override
    public List<Game> getUserGamesOfGenre(Integer userID, String genre) throws NoGamesFoundException, InvalidUserIDException {
        List<Game> games = getGamesByUserID(userID);
        List<Game> genreGames = new ArrayList<>();

        for (Game game : games) {
            if (game.getGenre().equalsIgnoreCase(genre.toLowerCase())) {
                genreGames.add(game);
            }
        }

        if (genreGames.size() == 0) {
            throw new NoGamesFoundException("No " + genre + " games found in user's library");
        }

        return genreGames;
    }

    @Override
    public List<Game> getUserGamesUnderHoursPlayed(Integer userID, Double hoursPlayed) throws NoGamesFoundException, InvalidUserIDException {
        List<Game> games = getGamesByUserID(userID);
        List<Game> playTimeGames = new ArrayList<>();

        for (Game game: games) {
            if (game.getHoursPlayed() <= hoursPlayed) {
                playTimeGames.add(game);
            }
        }

        if (playTimeGames.size() == 0) {
            throw new NoGamesFoundException("No games in user's library under " + hoursPlayed + " hours played");
        }

        return playTimeGames;
    }

    @Override
    public List<Game> getLeastPlayedGameInGenre(Integer userID, String genre) throws NoGamesFoundException, InvalidUserIDException {
        if (userID == null) {
            throw new InvalidUserIDException("User ID cannot be null");
        }
        if (genre == null) {
            throw new NoGamesFoundException("Genre cannot be null");
        }

        List<Game> toReturn = new ArrayList<>();
        double genreMin = Double.MAX_VALUE;
        for (Game toCheck : allGames.keySet()) {
            if (allGames.get(toCheck) == userID && toCheck.getGenre().equalsIgnoreCase(genre) && toCheck.getHoursPlayed() < genreMin) {
                genreMin = toCheck.getHoursPlayed();
            }
        }
        for (Game toCheck: allGames.keySet()) {
            if (toCheck.getGenre().equalsIgnoreCase(genre) && toCheck.getHoursPlayed() == genreMin) {
                toReturn.add(toCheck);
            }
        }
        if (toReturn.size() == 0) {
            throw new NoGamesFoundException("No eligible uncompleted games found owned by user " + userID);
        } else {
            return toReturn;
        }
    }

    @Override
    public Game changeCompletedStatus(Integer userID, Integer gameID) throws NoGamesFoundException {
        if (gameID == null) {
            throw new NoGamesFoundException("No changes made");
        }
        Game game = null;
        for (Game toCheck : allGames.keySet()) {
            if (allGames.get(toCheck) == userID && toCheck.getGameID() == gameID) {
                game = toCheck;
                break;
            }
        }
        if (game == null) {
            throw new NoGamesFoundException("No changes made");
        }
        game.setCompleted(!game.isCompleted());
        return game;
    }

    @Override
    public double getUserAveragePlayTime(Integer userID) throws InvalidUserIDException {
        if (userID == null) {
            throw new InvalidUserIDException("User ID cannot be null");
        }
        double sum = 0;
        int count = 0;
        for (Game game : allGames.keySet()) {
            if (game.getGameID() == userID) {
                sum += game.getHoursPlayed();
                count++;
            }
        }
        if (sum == 0) {
            return 0;
        } else {
            return sum / count;
        }
    }
}
