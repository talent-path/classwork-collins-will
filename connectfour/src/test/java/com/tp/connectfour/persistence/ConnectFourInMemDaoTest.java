package com.tp.connectfour.persistence;

import com.tp.connectfour.models.ConnectFourViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ConnectFourInMemDaoTest {

    @Autowired
    ConnectFourInMemDao dao;

    @Test
    public void testStartGameGoldenPath() {

        int oldAllGamesSize = dao.getAllGames().size();

        dao.startGame();

        assertEquals(oldAllGamesSize + 1, dao.getAllGames().size());
    }

    @Test
    public void testGetGameByIDGoldenPath() {

    }

    @Test
    public void testGetGameByIDNullID() {

    }

    @Test
    public void testGetGameByIDNullGame() {

    }

    @Test
    public void testGetAllGames() {

    }

    @Test
    public void testClearBoard() {

    }

    @Test
    public void testDeleteGameGoldenPath() {

    }

    @Test
    public void testDeleteGameNullGameID() {

    }

    @Test
    public void testDeleteGameInvalidGameID() {

    }
}