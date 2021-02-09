package com.tp.backlogtracker.persistence;

import com.tp.backlogtracker.models.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class GamePostgresDao implements GameDao {
    @Autowired
    JdbcTemplate template;

    @Override
    public int addGame(Game game) {
        return 0;
    }

    @Override
    public List<Game> getAllGames() {
        List<Game> allGames = template.query("select \"gameID\", \"name\", extract(epoch from \"playTime\")/3600 as \"hoursPlayed\"\n" +
                "from \"Games\";", new PartialGameMapper());
        return allGames;
    }

    class PartialGameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet resultSet, int i) throws SQLException {
            Game partiallyMappedGame = new Game();

            partiallyMappedGame.setGameID(resultSet.getInt("gameID"));
            partiallyMappedGame.setName(resultSet.getString("name"));
            partiallyMappedGame.setPlayTime(resultSet.getDouble("hoursPlayed"));

            return partiallyMappedGame;
        }
    }
}
