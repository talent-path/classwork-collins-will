package com.tp.connectfour.models;

public class ConnectFourViewModel {

    private Integer gameID;
    private int[][] board;
    boolean inProgress;

    private Integer playerMove;
    private Integer computerMove;

    public ConnectFourViewModel(Integer gameID)
    {
        this.gameID = gameID;
        this.board = new int[6][7];
        this.inProgress = true;

        this.playerMove = 1;
        this.computerMove = -1;
    }

    public ConnectFourViewModel(ConnectFourViewModel newGame)
    {
        this.gameID = newGame.gameID;
        this.board = newGame.board;
        this.inProgress = newGame.inProgress;

        this.playerMove = newGame.playerMove;
        this.computerMove = newGame.computerMove;
    }

    public void printBoard()
    {

        for (int i = 0; i < 6; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                if (board[i][j] == -1)
                {
                    System.out.print("|" + "O" + "|" );
                }
                else if (board[i][j] == 1)
                {
                    System.out.print("|" + "X" + "|");
                }
                else if (board[i][j] == 0)
                {
                    System.out.print("|" + " " + "|");
                }

            }
            System.out.println();
        }
    }

    public Integer getGameID() {
        return gameID;
    }

    public void setGameID(Integer gameID) {
        this.gameID = gameID;
    }

    public Integer getPlayerMove() {
        return playerMove;
    }

    public void setPlayerMove(Integer playerMove) {
        this.playerMove = playerMove;
    }

    public Integer getComputerMove() {
        return computerMove;
    }

    public void setComputerMove(Integer computerMove) {
        this.computerMove = computerMove;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public boolean isInProgress() {
        return inProgress;
    }

    public void setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
    }
}
