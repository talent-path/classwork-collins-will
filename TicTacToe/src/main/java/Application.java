import java.util.ArrayList;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        char[] board;
        boolean player1Turn = true;
        boolean player1Human = false;
        boolean player2Human = false;
        boolean gameOver = false;

        // Ask for number of games
        int numGames = Console.readInt("Welcome! Select how many games you would like to play (1-10)", 1, 10);
        int player1Wins = 0;
        int player2Wins = 0;
        int draws = 0;

        // Ask for number of players
        int numPlayers = Console.readInt("Enter the number of human players (0-2)", 0, 2);
        if (numPlayers == 2) {
            player1Human = true;
            player2Human = true;
        } else if (numPlayers == 1) {
            int playerChoice = Console.readInt("Are you player 1 (X) or player 2 (O)?", 1, 2);
            if (playerChoice == 1) {
                player1Human = true;
                player2Human = false;
            } else {
                player1Human = false;
                player2Human = true;
            }

        } else {
            player1Human = false;
            player2Human = false;
        }

        // Ask for board size
        int boardSize = Console.readInt("How big do you want the board? (3x3 - 7x7)", 3, 7);
        board = clearBoard(boardSize);

        // Game loop
        for (int i = 0; i < numGames; i++) {
            int moveCount = 0;
            printBoard(board);
            while (!gameOver) {
                if (player1Turn) {
                    System.out.println("Player 1's move!");

                    if (player1Human) {
                        board[playerMove(board) - 1] = 'x';
                    } else {
                        if (moveCount == 0 || (moveCount == 1 && board[4] == '.')) {
                            board[4] = 'x';
                        } else {
                            if (boardSize == 3) {
                                board[smartComputerMove(board, 'o')] = 'x';
                            } else {
                                board[computerMove(board)] = 'x';
                            }
                        }
                    }
                } else {
                    System.out.println("Player 2's move!");

                    if (player2Human) {
                        board[playerMove(board) - 1] = 'o';
                    } else {
                        if (moveCount == 0 || (moveCount == 1 && board[4] == '.')) {
                            board[4] = 'o';
                        } else {
                            if (boardSize == 3) {
                                board[smartComputerMove(board, 'x')] = 'o';
                            } else {
                                board[computerMove(board)] = 'o';
                            }
                        }
                    }
                }
                moveCount++;
                gameOver = (checkWinner(board) || moveCount == (boardSize * boardSize));
                    player1Turn = !player1Turn;
                printBoard(board);
            }

            if (moveCount != 9) {
                if (!player1Turn) {
                    System.out.println("X wins!");
                    player1Wins++;
                } else {
                    System.out.println("O wins!");
                    player2Wins++;
                }
            } else {
                System.out.println("It's a draw!");
                draws++;
            }
            printScore(player1Wins, player2Wins, draws);
            gameOver = false;
            board = clearBoard(boardSize);
        }
    }

    public static int playerMove(char[] board) {
        boolean validMove = false;
        int playerMove = -1;

        while (!validMove) {
            playerMove = Console.readInt("Please enter your move (1-" + board.length + "): ", 1, board.length);
            if (board[playerMove - 1] == '.') {
                validMove = true;
            }
        }

        return playerMove;
    }

    public static int computerMove(char[] board) {
        boolean validMove = false;
        int compMove = -1;

        while (!validMove) {
            compMove = Rng.randInt(0,board.length - 1);
            if (board[compMove] == '.') {
                validMove = true;
            }
        }

        return compMove;
    }

    // only works for 3x3
    public static int smartComputerMove(char[] board, char opponent) {
        ArrayList<Integer> goodMoves = new ArrayList<Integer>();
        ArrayList<Integer> defensiveMoves = new ArrayList<Integer>();

        // upper left
        if (board[0] == '.') {
            // row
            if (board[1] == board[2] && board[1] != '.') {
                if (board[1] == opponent) {
                    defensiveMoves.add(0);
                } else {
                    goodMoves.add(0);
                }
            }
            // column
            if (board[3] == board[6] && board[3] != '.') {
                if (board[3] == opponent) {
                    defensiveMoves.add(0);
                } else {
                    goodMoves.add(0);
                }
            }
            // diagonal
            if (board[4] == board[8] && board[4] != '.') {
                if (board[4] == opponent) {
                    defensiveMoves.add(0);
                } else {
                    goodMoves.add(0);
                }
            }
        }

        // upper middle
        if (board[1] == '.') {
            // row
            if (board[0] == board[2] && board[0] != '.') {
                if (board[0] == opponent) {
                    defensiveMoves.add(1);
                } else {
                    goodMoves.add(1);
                }
            }
            // column
            if (board[4] == board[7] && board[4] != '.') {
                if (board[4] == opponent) {
                    defensiveMoves.add(1);
                } else {
                    goodMoves.add(1);
                }
            }
        }

        // upper right
        if (board[2] == '.') {
            // row
            if (board[1] == board[0] && board[1] != '.') {
                if (board[1] == opponent) {
                    defensiveMoves.add(2);
                } else {
                    goodMoves.add(2);
                }
            }
            // column
            if (board[5] == board[8] && board[5] != '.') {
                if (board[5] == opponent) {
                    defensiveMoves.add(2);
                } else {
                    goodMoves.add(2);
                }
            }
            // diagonal
            if (board[4] == board[6] && board[4] != '.') {
                if (board[4] == opponent) {
                    defensiveMoves.add(2);
                } else {
                    goodMoves.add(2);
                }
            }
        }

        // middle left
        if (board[3] == '.') {
            // row
            if (board[4] == board[5] && board[4] != '.') {
                if (board[4] == opponent) {
                    defensiveMoves.add(3);
                } else {
                    goodMoves.add(3);
                }
            }
            // column
            if (board[0] == board[6] && board[0] != '.') {
                if (board[0] == opponent) {
                    defensiveMoves.add(3);
                } else {
                    goodMoves.add(3);
                }
            }
        }

        // center
        if (board[4] == '.') {
            // row
            if (board[3] == board[5] && board[3] != '.') {
                if (board[3] == opponent) {
                    defensiveMoves.add(4);
                } else {
                    goodMoves.add(4);
                }
            }
            // column
            if (board[1] == board[7] && board[1] != '.') {
                if (board[1] == opponent) {
                    defensiveMoves.add(4);
                } else {
                    goodMoves.add(4);
                }
            }
            // diagonals
            if (board[0] == board[8] && board[0] != '.') {
                if (board[0] == opponent) {
                    defensiveMoves.add(4);
                } else {
                    goodMoves.add(4);
                }
            }
            if (board[2] == board[6] && board[2] != '.') {
                if (board[2] == opponent) {
                    defensiveMoves.add(4);
                } else {
                    goodMoves.add(4);
                }
            }
        }

        // middle right
        if (board[5] == '.') {
            // row
            if (board[4] == board[3] && board[4] != '.') {
                if (board[4] == opponent) {
                    defensiveMoves.add(5);
                } else {
                    goodMoves.add(5);
                }
            }
            // column
            if (board[2] == board[8] && board[2] != '.') {
                if (board[2] == opponent) {
                    defensiveMoves.add(5);
                } else {
                    goodMoves.add(5);
                }
            }
        }

        // lower left
        if (board[6] == '.') {
            // row
            if (board[7] == board[8] && board[7] != '.') {
                if (board[7] == opponent) {
                    defensiveMoves.add(6);
                } else {
                    goodMoves.add(6);
                }
            }
            // column
            if (board[0] == board[3] && board[0] != '.') {
                if (board[0] == opponent) {
                    defensiveMoves.add(6);
                } else {
                    goodMoves.add(6);
                }
            }
            // diagonal
            if (board[4] == board[2] && board[4] != '.') {
                if (board[4] == opponent) {
                    defensiveMoves.add(6);
                } else {
                    goodMoves.add(6);
                }
            }
        }

        // lower center
        if (board[7] == '.') {
            // row
            if (board[6] == board[8] && board[6] != '.') {
                if (board[6] == opponent) {
                    defensiveMoves.add(7);
                } else {
                    goodMoves.add(7);
                }
            }
            // column
            if (board[4] == board[1] && board[4] != '.') {
                if (board[4] == opponent) {
                    defensiveMoves.add(7);
                } else {
                    goodMoves.add(7);
                }
            }
        }

        // lower right
        if (board[8] == '.') {
            // row
            if (board[7] == board[6] && board[7] != '.') {
                if (board[7] == opponent) {
                    defensiveMoves.add(8);
                } else {
                    goodMoves.add(8);
                }
            }
            // column
            if (board[2] == board[5] && board[2] != '.') {
                if (board[2] == opponent) {
                    defensiveMoves.add(8);
                } else {
                    goodMoves.add(8);
                }
            }
            // diagonal
            if (board[4] == board[0] && board[4] != '.') {
                if (board[4] == opponent) {
                    defensiveMoves.add(8);
                } else {
                    goodMoves.add(8);
                }
            }
        }

        if (goodMoves.size() > 0) {
            return goodMoves.get(Rng.randInt(0, goodMoves.size() - 1));
        } else if (defensiveMoves.size() > 0) {
            return defensiveMoves.get(Rng.randInt(0, defensiveMoves.size() - 1));
        } else {
            return computerMove(board);
        }
    }

    public static void printBoard(char[] board) {
        /*for (int i = 0; i < 9; i++) {
            System.out.print(board[i] + " ");
            if ((i + 1) % 3 == 0) {
                System.out.println();
            }
        }

        System.out.println();*/

        // Get proper board width
        String horizontalLine = "";
        int width = (int) Math.sqrt(board.length);
        for (int i = 0; i < width; i++) {
            horizontalLine += "----";
        }
        horizontalLine += "-";

        for (int i = 0; i < width; i++) {
            System.out.println(horizontalLine);
            for (int j = 0; j < width; j++) {
                System.out.print("| " + board[width * i + j] + " ");
            }
            System.out.println("|");
        }
        System.out.println(horizontalLine);
    }

    public static boolean checkWinner(char[] board) {
        int width = (int) Math.sqrt(board.length);

        // check rows
        for (int i = 0; i < board.length; i+=width) {
            // get indexes to check
            int[] rowIndices = new int[width];
            int rowCurrent = i;
            for (int j = 0; j < width; j++) {
                rowIndices[j] = rowCurrent;
                rowCurrent++;
            }

            // check if all are equal
            boolean rowMatching = true;
            char beginningRowPiece = board[rowIndices[0]];
            int currentRowIndex = 1;

            if (beginningRowPiece == '.') {
                rowMatching = false;
            }

            while (rowMatching && currentRowIndex < rowIndices.length) {
                if (board[rowIndices[currentRowIndex]] != beginningRowPiece) {
                    rowMatching = false;
                }
                currentRowIndex++;
            }

            if (rowMatching) {
                return true;
            }
        }

        // check columns
        for (int i = 0; i < width; i++) {
            // get indexes to check
            int[] columnIndices = new int[width];
            int columnCurrent = i;
            for (int j = 0; j < width; j++) {
                columnIndices[j] = columnCurrent;
                columnCurrent += width;
            }

            // check if all are equal
            boolean columnMatching = true;
            char beginningColumnPiece = board[columnIndices[0]];
            int currentColumnIndex = 1;

            if (beginningColumnPiece == '.') {
                columnMatching = false;
            }

            while (columnMatching && currentColumnIndex <= columnIndices.length) {
                if (board[columnIndices[currentColumnIndex]] != beginningColumnPiece) {
                    columnMatching = false;
                }
                currentColumnIndex++;
            }

            if (columnMatching) {
                return true;
            }
        }
        // check diagonal (upper left to lower right)
        int[] diag1Indices = new int[width];
        int diag1Current = 0;
        for (int j = 0; j < width; j++) {
            diag1Indices[j] = diag1Current;
            diag1Current += (width + 1);
        }

        boolean diag1Matching = true;
        char beginningDiag1Piece = board[diag1Indices[0]];
        int currentDiag1Index = 1;

        if (beginningDiag1Piece == '.') {
            diag1Matching = false;
        }

        while (diag1Matching && currentDiag1Index < diag1Indices.length) {
            if (board[diag1Indices[currentDiag1Index]] != beginningDiag1Piece) {
                diag1Matching = false;
            }
            currentDiag1Index++;
        }

        if (diag1Matching) {
            return true;
        }

        // check diagonal (upper right to lower left)
        int[] diag2Indices = new int[width];
        int diag2Current = width - 1;
        for (int j = 0; j < width; j++) {
            diag2Indices[j] = diag2Current;
            diag2Current += (width - 1);
        }

        boolean diag2Matching = true;
        char beginningdiag2Piece = board[diag2Indices[0]];
        int currentdiag2Index = 1;

        if (beginningdiag2Piece == '.') {
            diag2Matching = false;
        }

        while (diag2Matching && currentdiag2Index < diag2Indices.length) {
            if (board[diag2Indices[currentdiag2Index]] != beginningdiag2Piece) {
                diag2Matching = false;
            }
            currentdiag2Index++;
        }

        if (diag2Matching) {
            return true;
        }

        return false;
    }

    public static void printScore(int player1, int player2, int draws) {
        System.out.println("Current Score:");
        System.out.println("X: " + player1);
        System.out.println("O: " + player2);
        System.out.println("Draws: " + draws);
    }

    public static char[] clearBoard(int size) {
        char[] newBoard = new char[size * size];

        for (int i = 0; i < newBoard.length; i++) {
            newBoard[i] = '.';
        }

        return newBoard;
    }
}
