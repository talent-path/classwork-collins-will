package com.tp.backlogtracker.services;

import com.tp.backlogtracker.exceptions.InvalidUserIDException;
import com.tp.backlogtracker.exceptions.NoGamesFoundException;
import com.tp.backlogtracker.models.Game;
import com.tp.backlogtracker.models.User;
import com.tp.backlogtracker.persistence.GameInMemDao;
import com.tp.backlogtracker.persistence.UserInMemDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("serviceTesting")
class BacklogServiceTest {

    @Autowired
    BacklogService toTest;

    @Autowired
    JdbcTemplate template;

    @BeforeEach
    public void setup() {
        toTest.gameDao = new GameInMemDao();
        toTest.userDao = new UserInMemDao();

        Game game = new Game();
        game.setGameID(1);
        game.setName("testGame");
        game.setHoursPlayed(10);
        game.setUserName("testUser");
        game.setGenre("testGenre");
        game.setCompleted(true);
        toTest.gameDao.addGame(1, game);

        toTest.userDao.addUser(1, "testUser");
    }

    @Test
    public void testGetGamesByUserIDGoldenPath() {
        List<Game> games = null;
        try {
            games = toTest.getGamesByUserID(1);
        } catch (NoGamesFoundException | InvalidUserIDException ex) {
            fail();
        }
        assertEquals(1, games.size());
        Game game = games.get(0);
        assertEquals(1, game.getGameID());
        assertEquals("testGame", game.getName());
        assertEquals(10, game.getHoursPlayed());
        assertEquals("testUser", game.getUserName());
        assertEquals("testGenre", game.getGenre());
        assertTrue(game.isCompleted());
    }

    @Test
    public void testGetGamesByUserIDNullUserID() {
        assertThrows(InvalidUserIDException.class, () -> toTest.getGamesByUserID(null));
    }

    @Test
    public void testGetGamesByUserIDNoGamesFound() {
        assertThrows(NoGamesFoundException.class, () -> toTest.getGamesByUserID(-1));
    }

    @Test
    public void testGetUserByIDGoldenPath() {
        User user = null;
        try {
            user = toTest.getUserByID(1);
        } catch (NoGamesFoundException | InvalidUserIDException ex) {
            fail();
        }
        assertEquals(1, user.getUserID());
        assertEquals("testUser", user.getName());
        assertNotNull(user.getLibrary());
        Game game = user.getLibrary().get(0);
        assertEquals(1, game.getGameID());
        assertEquals("testGame", game.getName());
        assertEquals(10, game.getHoursPlayed());
        assertEquals("testUser", game.getUserName());
        assertEquals("testGenre", game.getGenre());
        assertTrue(game.isCompleted());
    }
}