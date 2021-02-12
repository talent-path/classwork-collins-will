package com.tp.backlogtracker.persistence;

import com.tp.backlogtracker.exceptions.InvalidUserIDException;
import com.tp.backlogtracker.exceptions.NoGamesFoundException;
import com.tp.backlogtracker.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("daoTesting")
class UserPostgresDaoTest {

    @Autowired
    UserDao toTest;

    @Autowired
    JdbcTemplate template;

    @BeforeEach
    public void setup() {
        template.update("truncate \"UserGames\",\"GameGenres\",\"Games\",\"Genres\",\"Users\" restart identity;");
        template.update("insert into \"Users\" (\"userID\",\"name\") values('1','testUser'),('2','noGames');");
        template.update("insert into \"Games\" (\"gameID\",\"name\") values('1','testGame'),('2','testGame2'),('3','testGame3');\n" +
                "insert into \"Genres\" (\"genreID\",\"name\") values('1','testGenre'),('2','testGenre2');\n" +
                "insert into \"GameGenres\" (\"gameID\",\"genreID\") values('1','1'),('2','2'),('3','1');\n" +
                "insert into \"UserGames\" (\"userID\",\"gameID\",\"completed\",\"playTime\") values('1','1','true','10 hours'),('1','2','false','20 hours'),('1','3','false','15 hours');");
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
    }

    @Test
    public void testGetUserByIDNullID() {
        assertThrows(InvalidUserIDException.class, () -> toTest.getUserByID(null));
    }

    @Test
    public void testGetUserByIDNoUserFound() {
        assertThrows(InvalidUserIDException.class, () -> toTest.getUserByID(-1));
    }

}