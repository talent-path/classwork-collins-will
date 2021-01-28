package com.tp.connectfour.persistence;

import com.tp.connectfour.exceptions.InvalidGameIDException;
import com.tp.connectfour.exceptions.NullGameIDException;
import com.tp.connectfour.models.ConnectFourViewModel;

import java.util.List;

public interface ConnectFourDao {


    ConnectFourViewModel getGameByID(Integer gameID) throws NullGameIDException;

    List<ConnectFourViewModel> getAllGames();

    void clearBoard(Integer gameID) throws NullGameIDException, InvalidGameIDException;

    ConnectFourViewModel startGame();

    void deleteGame(Integer gameId) throws InvalidGameIDException;


}
