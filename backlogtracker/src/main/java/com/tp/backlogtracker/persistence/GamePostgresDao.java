package com.tp.backlogtracker.persistence;

import com.tp.backlogtracker.exceptions.InvalidUserIDException;
import com.tp.backlogtracker.exceptions.NoGamesFoundException;
import com.tp.backlogtracker.models.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile({"production","daoTesting"})
public class GamePostgresDao implements GameDao {
    @Autowired
    JdbcTemplate template;

    @Override
    public int addGame(Integer userID, Game game) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Game> getGamesByUserID(Integer userID) throws NoGamesFoundException, InvalidUserIDException {
        if (userID == null) {
            throw new InvalidUserIDException("User ID cannot be null");
        }

        List<Game> allUserGames = new ArrayList<>();

        try {
        allUserGames = template.query(
                "select ga.\"gameID\", ga.\"name\" as \"gameName\", ge.\"name\" as \"genreName\", u.\"name\" as \"userName\", extract(epoch from ug.\"playTime\")/3600 as \"hoursPlayed\", ug.\"completed\"\n" +
                        "from \"Games\" as ga\n" +
                        "inner join \"GameGenres\" as gg on ga.\"gameID\" = gg.\"gameID\"\n" +
                        "inner join \"Genres\" as ge on gg.\"genreID\" = ge.\"genreID\"\n" +
                        "inner join \"UserGames\" as ug on ug.\"gameID\" = ga.\"gameID\"\n" +
                        "inner join \"Users\" as u on ug.\"userID\" = u.\"userID\"\n" +
                        "where ug.\"userID\" = ?;",
                new GameMapper(),
                userID);
        } catch (EmptyResultDataAccessException ex) {
            throw new NoGamesFoundException("No games found owned by user ID " + userID);
        }
        if (allUserGames.size() == 0) {
            throw new NoGamesFoundException("No games found owned by user ID " + userID);
        }
        return allUserGames;
    }

    @Override
    public List<Game> getUserGamesOfGenre(Integer userID, String genre) throws NoGamesFoundException, InvalidUserIDException {
        if (userID == null) {
            throw new InvalidUserIDException("User ID cannot be null");
        }
        if (genre == null) {
            throw new NoGamesFoundException("Genre cannot be null");
        }
        List<Game> genreGames = new ArrayList<>();

        try {
            genreGames = template.query(
                    "select ga.\"gameID\", ga.\"name\" as \"gameName\", ge.\"name\" as \"genreName\", u.\"name\" as \"userName\", extract(epoch from ug.\"playTime\")/3600 as \"hoursPlayed\", ug.\"completed\"\n" +
                            "from \"Games\" as ga\n" +
                            "inner join \"GameGenres\" as gg on ga.\"gameID\" = gg.\"gameID\"\n" +
                            "inner join \"Genres\" as ge on gg.\"genreID\" = ge.\"genreID\"\n" +
                            "inner join \"UserGames\" as ug on ug.\"gameID\" = ga.\"gameID\"\n" +
                            "inner join \"Users\" as u on ug.\"userID\" = u.\"userID\"\n" +
                            "where ug.\"userID\" = ? and lower(ge.\"name\") = ?;",
                    new GameMapper(),
                    userID,
                    genre.toLowerCase());
        } catch (EmptyResultDataAccessException ex) {
            throw new NoGamesFoundException("No " + genre + " games found in user's library");
        }
        if (genreGames.size() == 0) {
            throw new NoGamesFoundException("No " + genre + " games found in user's library");
        }
        return genreGames;
    }

    @Override
    public List<Game> getUserGamesUnderHoursPlayed(Integer userID, Double hoursPlayed) throws NoGamesFoundException, InvalidUserIDException {
        if (userID == null) {
            throw new InvalidUserIDException("User ID cannot be null");
        }
        if (hoursPlayed == null) {
            throw new NoGamesFoundException("Genre cannot be null");
        }
        List<Game> games = new ArrayList<>();

        try {
            games = template.query(
                    "select ga.\"gameID\", ga.\"name\" as \"gameName\", ge.\"name\" as \"genreName\", u.\"name\" as \"userName\", extract(epoch from ug.\"playTime\")/3600 as \"hoursPlayed\", ug.\"completed\"\n" +
                            "from \"Games\" as ga\n" +
                            "inner join \"GameGenres\" as gg on ga.\"gameID\" = gg.\"gameID\"\n" +
                            "inner join \"Genres\" as ge on gg.\"genreID\" = ge.\"genreID\"\n" +
                            "inner join \"UserGames\" as ug on ug.\"gameID\" = ga.\"gameID\"\n" +
                            "inner join \"Users\" as u on ug.\"userID\" = u.\"userID\"\n" +
                            "where ug.\"userID\" = ? and extract(epoch from ug.\"playTime\")/3600 < ?;",
                    new GameMapper(),
                    userID,
                    hoursPlayed);
        } catch (EmptyResultDataAccessException ex) {
            throw new NoGamesFoundException("No games in user's library under " + hoursPlayed + " hours played");
        }
        if (games.size() == 0) {
            throw new NoGamesFoundException("No games in user's library under " + hoursPlayed + " hours played");
        }
        return games;
    }

    @Override
    public List<Game> getLeastPlayedGameInGenre(Integer userID, String genre) throws NoGamesFoundException, InvalidUserIDException {
        if (userID == null) {
            throw new InvalidUserIDException("User ID cannot be null");
        }
        if (genre == null) {
            throw new NoGamesFoundException("Genre cannot be null");
        }

        List<Game> genreGames = getUserGamesOfGenre(userID, genre);
        Double fewestHoursPlayed;
        try {
            fewestHoursPlayed = template.queryForObject(
                    "select ge.\"name\" as \"genreName\",min(extract(epoch from ug.\"playTime\")/3600) as \"leastTime\"\n" +
                            "from \"UserGames\" as ug\n" +
                            "inner join \"Games\" as ga on ug.\"gameID\" = ga.\"gameID\"\n" +
                            "inner join \"GameGenres\" as gg on ga.\"gameID\" = gg.\"gameID\"\n" +
                            "inner join \"Genres\" as ge on gg.\"genreID\" = ge.\"genreID\"\n" +
                            "where ug.\"userID\" = ? and not \"completed\" and lower(ge.\"name\") = ?\n" +
                            "group by ge.\"name\";",
                    new DoubleMapper("leastTime"),
                    userID,
                    genre.toLowerCase());
        } catch (EmptyResultDataAccessException ex) {
            throw new NoGamesFoundException("No eligible uncompleted games found owned by user " + userID);
        }
        List<Game> toReturn = new ArrayList<>();
        for (Game toCheck : genreGames) {
            if (toCheck.getHoursPlayed() == fewestHoursPlayed) {
                toReturn.add(toCheck);
            }
        }
        return toReturn;
    }

    @Override
    public Game changeCompletedStatus(Integer userID, Integer gameID) throws NoGamesFoundException {
        int switchResult = template.update("update \"UserGames\" set \"completed\" = not \"completed\" where \"userID\" = ? and \"gameID\" = ?;",
                userID,
                gameID);

        if (switchResult < 1) {
            throw new NoGamesFoundException("No changes made");
        } else {
            return template.queryForObject(
                    "select ga.\"gameID\", ga.\"name\" as \"gameName\", ge.\"name\" as \"genreName\", u.\"name\" as \"userName\", extract(epoch from ug.\"playTime\")/3600 as \"hoursPlayed\", ug.\"completed\"\n" +
                            "from \"Games\" as ga\n" +
                            "inner join \"GameGenres\" as gg on ga.\"gameID\" = gg.\"gameID\"\n" +
                            "inner join \"Genres\" as ge on gg.\"genreID\" = ge.\"genreID\"\n" +
                            "inner join \"UserGames\" as ug on ug.\"gameID\" = ga.\"gameID\"\n" +
                            "inner join \"Users\" as u on ug.\"userID\" = u.\"userID\"\n" +
                            "where ug.\"userID\" = ? and ug.\"gameID\" = ?;",
                    new GameMapper(),
                    userID,
                    gameID);
        }
    }

    @Override
    public double getUserAveragePlayTime(Integer userID) throws InvalidUserIDException {
        if (userID == null) {
            throw new InvalidUserIDException("User ID cannot be null");
        }

        double avgPlayTime = 0;

        try {
            avgPlayTime = template.queryForObject(
                    "select avg(extract(epoch from \"playTime\")/3600) as \"avgPlayTime\"\n" +
                            "from \"UserGames\"\n" +
                            "where \"userID\" = ?\n" +
                            "group by \"userID\";",
                    new DoubleMapper("avgPlayTime"),
                    userID);
        } catch (EmptyResultDataAccessException ex) {
            return 0;
        }

        return avgPlayTime;
    }

    @Override
    public int getNumOfUncompletedGames(Integer userID) throws InvalidUserIDException {
        if (userID == null) {
            throw new InvalidUserIDException("User ID cannot be null");
        }
        return template.queryForObject(
                "select count(*) as \"count\"\n" +
                        "from \"UserGames\"\n" +
                        "where \"userID\" = ? and \"completed\" = 'false';",
                new IntMapper("count"),
                userID);
    }
}
