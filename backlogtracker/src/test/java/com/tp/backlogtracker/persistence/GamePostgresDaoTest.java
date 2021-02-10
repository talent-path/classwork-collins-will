package com.tp.backlogtracker.persistence;

import com.tp.backlogtracker.exceptions.InvalidUserIDException;
import com.tp.backlogtracker.exceptions.NoGamesFoundException;
import com.tp.backlogtracker.models.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("daoTesting")
class GamePostgresDaoTest {

    @Autowired
    GamePostgresDao toTest;

    @Autowired
    JdbcTemplate template;

    @BeforeEach
    public void setup() {
        template.update("truncate \"UserGames\",\"GameGenres\",\"Games\",\"Genres\",\"Users\" restart identity;");
        template.update("insert into \"Users\" (\"userID\",\"name\") values('1','testUser');");
        template.update("insert into \"Games\" (\"gameID\",\"name\") values('1','testGame');\n" +
                "insert into \"Genres\" (\"genreID\",\"name\") values('1','testGenre');\n" +
                "insert into \"GameGenres\" (\"gameID\",\"genreID\") values('1','1');\n" +
                "insert into \"UserGames\" (\"userID\",\"gameID\",\"completed\",\"playTime\") values('1','1','true','10 hours');");
    }

    @Test
    public void testGetGamesByUserIDGoldenPath() {
        List<Game> games = null;
        try {
            games = toTest.getGamesByUserID(1);
        } catch (NoGamesFoundException | InvalidUserIDException ex) {
            fail();
        }
        Game game = games.get(0);
        assertEquals(1, game.getGameID());
        assertEquals("testGame", game.getName());
        assertEquals(10, game.getHoursPlayed());
        assertEquals("testUser", game.getUserName());
        assertEquals("testGenre", game.getGenre());
        assertEquals(true, game.isCompleted());
    }

    @Test
    public void testGetGamesByUserIDNullID() {
        assertThrows(InvalidUserIDException.class, () -> toTest.getGamesByUserID(null));
    }

    @Test
    public void testGetGamesByUserIDNoGamesFound() {
        assertThrows(NoGamesFoundException.class, () -> toTest.getGamesByUserID(-1));
    }

}