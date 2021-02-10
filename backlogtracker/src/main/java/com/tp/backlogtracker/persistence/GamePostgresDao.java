package com.tp.backlogtracker.persistence;

import com.tp.backlogtracker.exceptions.InvalidUserIDException;
import com.tp.backlogtracker.exceptions.NoGamesFoundException;
import com.tp.backlogtracker.models.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@Profile({"production","daoTesting"})
public class GamePostgresDao implements GameDao {
    @Autowired
    JdbcTemplate template;

    @Override
    public int addGame(Integer userID, Game game) {
        return 0;
    }

    @Override
    public List<Game> getGamesByUserID(Integer userID) throws NoGamesFoundException, InvalidUserIDException {
        if (userID == null) {
            throw new InvalidUserIDException("User ID cannot be null");
        }

        List<Game> allUserGames = template.query(
                "select ga.\"gameID\", ga.\"name\" as \"gameName\", ge.\"name\" as \"genreName\", u.\"name\" as \"userName\", extract(epoch from ug.\"playTime\")/3600 as \"hoursPlayed\", ug.\"completed\"\n" +
                        "from \"Games\" as ga\n" +
                        "inner join \"GameGenres\" as gg on ga.\"gameID\" = gg.\"gameID\"\n" +
                        "inner join \"Genres\" as ge on gg.\"genreID\" = ge.\"genreID\"\n" +
                        "inner join \"UserGames\" as ug on ug.\"gameID\" = ga.\"gameID\"\n" +
                        "inner join \"Users\" as u on ug.\"userID\" = u.\"userID\"\n" +
                        "where ug.\"userID\" = ?;",
                new GameMapper(),
                userID);
        if (allUserGames.size() == 0) {
            throw new NoGamesFoundException("No games found owned by user ID " + userID);
        }
        return allUserGames;
    }

    class PartialGameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet resultSet, int i) throws SQLException {
            Game partiallyMappedGame = new Game();

            partiallyMappedGame.setGameID(resultSet.getInt("gameID"));
            partiallyMappedGame.setName(resultSet.getString("name"));

            return partiallyMappedGame;
        }
    }

    class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet resultSet, int i) throws SQLException {
            Game mappedGame = new Game();

            mappedGame.setGameID(resultSet.getInt("gameID"));
            mappedGame.setName(resultSet.getString("gameName"));
            mappedGame.setGenre(resultSet.getString("genreName"));
            mappedGame.setUserName(resultSet.getString("userName"));
            mappedGame.setHoursPlayed(resultSet.getDouble("hoursPlayed"));
            mappedGame.setCompleted(resultSet.getBoolean("completed"));

            return mappedGame;
        }
    }
}
