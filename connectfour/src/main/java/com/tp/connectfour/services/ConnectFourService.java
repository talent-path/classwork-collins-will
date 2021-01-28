package com.tp.connectfour.services;


import com.tp.connectfour.RNG;
import com.tp.connectfour.exceptions.GameOverException;
import com.tp.connectfour.exceptions.InvalidGameIDException;
import com.tp.connectfour.exceptions.InvalidMoveException;
import com.tp.connectfour.exceptions.NullGameIDException;
import com.tp.connectfour.models.ConnectFourViewModel;
import com.tp.connectfour.persistence.ConnectFourDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConnectFourService {

    @Autowired
    ConnectFourDao dao;

    public ConnectFourViewModel startGame() {
        return dao.startGame();
    }

    public List<ConnectFourViewModel> getAllGames() {
        return dao.getAllGames();
    }

    public ConnectFourViewModel getGameByID(Integer gameID) throws NullGameIDException, InvalidGameIDException
    {
        ConnectFourViewModel game = null;

        game = dao.getGameByID(gameID);
        if (game == null) {
            throw new InvalidGameIDException("Cannot find a game with ID " + gameID);
        }

        return game;
    }

    public void deleteGame(Integer gameID) throws InvalidGameIDException {
        dao.deleteGame(gameID);
    }

    public void clearBoard(Integer gameID) throws NullGameIDException, InvalidGameIDException
    {
        dao.clearBoard(gameID);
    }

    public ConnectFourViewModel makeMove(Integer gameID, Integer columnMove) throws InvalidMoveException, NullGameIDException, InvalidGameIDException, GameOverException{
        if (columnMove == null) {
            throw new InvalidMoveException("Column cannot be null.");
        }
        if (gameID == null) {
            throw new NullGameIDException("Game ID cannot be null.");
        }
        if (columnMove < 1 || columnMove > 7) {
            throw new InvalidMoveException("Column out of bounds.");
        }

        ConnectFourViewModel game;

        game = dao.getGameByID(gameID);
        if (game == null) {
            throw new InvalidGameIDException("Cannot find a game with ID " + gameID);
        }

        if (!game.isInProgress()) {
            throw new GameOverException("Game has already been completed.");
        }

        int num = checkWinner(game.getBoard());

        if (num > -2)
        {
            game.setInProgress(false);
            if (num == 1)
            {
                System.out.println("--- *** PLAYER WINS *** ---");
            }
            else if (num == -1)
            {
                System.out.println("--- *** COMPUTER WINS *** ---");
            }
            else if (num == 0)
            {
                System.out.println("--- *** DRAW *** ---");
            }

        }

        int rowMove = 6;

        for (int i = 5; i >= 0; i--) {
            if (i < rowMove && game.getBoard()[i][columnMove - 1] == 0) {
                rowMove = i;
                break;
            }
        }

        if (rowMove == 6) {
            throw new InvalidMoveException("Chosen column is full.");
        }

        game.getBoard()[rowMove][columnMove - 1] = game.getPlayerMove();

        num = checkWinner(game.getBoard());

        if (num > -2)
        {
            game.setInProgress(false);
            if (num == 1)
            {
                System.out.println("--- *** PLAYER WINS *** ---");
            }
            else if (num == -1)
            {
                System.out.println("--- *** COMPUTER WINS *** ---");
            }
            else if (num == 0)
            {
                System.out.println("--- *** DRAW *** ---");
            }

        }

        game = computerMove(game);

        num = checkWinner(game.getBoard());

        if (num > -2)
        {
            game.setInProgress(false);
            if (num == 1)
            {
                System.out.println("--- *** PLAYER WINS *** ---");
            }
            else if (num == -1)
            {
                System.out.println("--- *** COMPUTER WINS *** ---");
            }
            else if (num == 0)
            {
                System.out.println("--- *** DRAW *** ---");
            }

        }

        //dao.getAllGames().add(game.getGameID(), game);
        return game;
    }

    public ConnectFourViewModel computerMove(ConnectFourViewModel game) {

        ConnectFourViewModel copyGame = game;

        int compMove = RNG.randInt(0, 6);

        int rowMove = 6;

        while (rowMove > 5) {
            for (int i = 5; i >= 0; i--) {
                if (i < rowMove && copyGame.getBoard()[i][compMove] == 0) {
                    rowMove = i;
                    break;
                }
            }
            if (rowMove > 5) {
                compMove = RNG.randInt(0, 6);
            }
        }

        copyGame.getBoard()[rowMove][compMove] = copyGame.getComputerMove();

        return copyGame;

    }

    public Integer checkWinner(int[][] board)
    {
        int gameState = -2;
        if (checkDraw(board))
            return 0;
        else if (checkColumn(board) > gameState)
        {
            return checkColumn(board);
        }
        else if (checkRow(board) > gameState)
        {
            return checkRow(board);
        }
        else if (checkDiagonal(board) > gameState)
        {
            return checkDiagonal(board);
        }
        else
            return gameState;

    }

    public boolean checkDraw(int[][] board) {

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }

        return true;

    }

    public Integer checkRow(int[][] board) {

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == board[i][j + 1] && board[i][j] == board[i][j + 2]
                        && board[i][j] == board[i][j + 3] && board[i][j] != 0) {
                    return board[i][j];
                }
            }
        }

        return -2;

    }

    public Integer checkColumn(int[][] board) {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 6; j++) {
                if (board[i][j] == board[i + 1][j] && board[i][j] == board[i + 2][j]
                        && board[i][j] == board[i + 3][j] && board[i][j] != 0) {
                    return board[i][j];
                }
            }
        }

        return -2;

    }

    public Integer checkDiagonal(int[][] board) {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == board[i + 1][j + 1] && board[i][j] == board[i + 2][j + 2]
                        && board[i][j] == board[i + 3][j + 3] && board[i][j] != 0)
                {
                    return board[i][j];
                }
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 4; j < 7; j++) {
                if (board[i][j] == board[i + 1][j - 1] && board[i][j] == board[i + 2][j - 2]
                        && board[i][j] == board[i + 3][j - 3] && board[i][j] != 0) {
                    return board[i][j];
                }
            }
        }

        return -2;
    }

}
