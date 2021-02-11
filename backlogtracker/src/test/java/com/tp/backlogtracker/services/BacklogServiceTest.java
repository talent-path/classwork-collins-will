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

    public void addMoreGames() {
        Game game2 = new Game();
        game2.setGameID(2);
        game2.setName("New Vegas");
        game2.setHoursPlayed(40);
        game2.setUserName("testUser");
        game2.setGenre("RPG");
        game2.setCompleted(true);
        toTest.gameDao.addGame(1, game2);

        Game game3 = new Game();
        game3.setGameID(3);
        game3.setName("Half-Life 3");
        game3.setHoursPlayed(99);
        game3.setUserName("testUser");
        game3.setGenre("Shooter");
        game3.setCompleted(false);
        toTest.gameDao.addGame(1, game3);
    }

    @BeforeEach
    public void setup() {
        toTest.gameDao = new GameInMemDao();
        toTest.userDao = new UserInMemDao();

        Game game = new Game();
        game.setGameID(1);
        game.setName("testGame");
        game.setHoursPlayed(10);
        game.setUserName("testUser");
        game.setGenre("Testgenre");
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
        assertEquals("Testgenre", game.getGenre());
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
        assertEquals("Testgenre", game.getGenre());
        assertTrue(game.isCompleted());
    }

    @Test
    public void testGetUserByIDNullID() {
        assertThrows(InvalidUserIDException.class, () -> toTest.getUserByID(null));
    }

    @Test
    public void testGetUserByIDNoUserFound() {
        assertThrows(InvalidUserIDException.class, () -> toTest.getUserByID(-1));
    }

    @Test
    public void testSortUserGamesByGenreGoldenPath() {
        addMoreGames();
        List<Game> sortedGenreGames = null;
        try {
            sortedGenreGames = toTest.sortUserGamesByGenre(1).getLibrary();
        } catch (NoGamesFoundException | InvalidUserIDException ex) {
            fail();
        }
        assertEquals("New Vegas",sortedGenreGames.get(0).getName());
        assertEquals("Half-Life 3",sortedGenreGames.get(1).getName());
        assertEquals("testGame",sortedGenreGames.get(2).getName());
    }

    @Test
    public void testSortUserGamesByGenreNullUserID() {
        assertThrows(InvalidUserIDException.class, () -> toTest.sortUserGamesByGenre(null));
    }

    @Test
    public void testSortUserGamesByGenreNoUserFound() {
        assertThrows(InvalidUserIDException.class, () -> toTest.sortUserGamesByGenre(-1));
    }

    @Test
    public void testSortUserGamesByGenreNoGamesFound() {
        toTest.userDao.addUser(2, "noGames");
        assertThrows(NoGamesFoundException.class, () -> toTest.sortUserGamesByGenre(2));
    }

    @Test
    public void testGetUserGamesByGenreGoldenPath() {
        List<Game> games = null;
        try {
            games = toTest.getUserGamesByGenre(1, "testGenre").getLibrary();
        } catch (NoGamesFoundException | InvalidUserIDException ex) {
            fail();
        }
        assertEquals(1, games.size());
        Game game = games.get(0);
        assertEquals(1, game.getGameID());
        assertEquals("testGame", game.getName());
        assertEquals(10, game.getHoursPlayed());
        assertEquals("testUser", game.getUserName());
        assertEquals("Testgenre", game.getGenre());
        assertTrue(game.isCompleted());
    }

    @Test
    public void testGetUserGamesByGenreNullUserID() {
        assertThrows(InvalidUserIDException.class, () -> toTest.getUserGamesByGenre(null, "testGenre"));
    }

    @Test
    public void testGetUserGamesByGenreNoUserFound() {
        assertThrows(InvalidUserIDException.class, () -> toTest.getUserGamesByGenre(-1, "testGenre"));
    }

    @Test
    public void testGetUserGamesByGenreNoGamesFound() {
        toTest.userDao.addUser(2, "noGames");
        assertThrows(NoGamesFoundException.class, () -> toTest.getUserGamesByGenre(2, "testGenre"));
    }

    @Test
    public void testGetUserGamesByGenreBadGenre() {
        assertThrows(NoGamesFoundException.class, () -> toTest.getUserGamesByGenre(1, "noGenre"));
    }

    @Test
    public void testSortUserGamesByHoursPlayedGoldenPath() {
        addMoreGames();
        List<Game> sortedPlayTimeGames = null;
        try {
            sortedPlayTimeGames = toTest.sortUserGamesByHoursPlayed(1).getLibrary();
        } catch (NoGamesFoundException | InvalidUserIDException ex) {
            fail();
        }
        assertEquals("testGame",sortedPlayTimeGames.get(0).getName());
        assertEquals("New Vegas",sortedPlayTimeGames.get(1).getName());
        assertEquals("Half-Life 3",sortedPlayTimeGames.get(2).getName());
    }

    @Test
    public void testSortUserGamesByHoursPlayedNullUserID() {
        assertThrows(InvalidUserIDException.class, () -> toTest.sortUserGamesByHoursPlayed(null));
    }

    @Test
    public void testSortUserGamesByHoursPlayedNoUserFound() {
        assertThrows(InvalidUserIDException.class, () -> toTest.sortUserGamesByHoursPlayed(-1));
    }

    @Test
    public void testSortUserGamesByHoursPlayedNoGamesFound() {
        toTest.userDao.addUser(2, "noGames");
        assertThrows(NoGamesFoundException.class, () -> toTest.sortUserGamesByHoursPlayed(2));
    }
}