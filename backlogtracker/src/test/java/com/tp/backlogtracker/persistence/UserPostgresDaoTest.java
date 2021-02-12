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
        template.update("insert into \"Users\" (\"userID\",\"name\") values('1','testUser');");
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
        assertEquals(null, user.getLibrary());
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
    public void getUserAvgPlayTimeGoldenPath() {

    }

}