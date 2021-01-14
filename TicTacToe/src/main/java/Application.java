import java.util.ArrayList;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        char[] board = {'.','.','.','.','.','.','.','.','.'};
        boolean player1Turn = true;
        boolean twoPlayers = false;
        boolean gameOver = false;
        printBoard(board);

        // Ask for number of games
        int numGames = Console.readInt("Welcome! Select how many games you would like to play (1-10)", 1, 10);
        int player1Wins = 0;
        int player2Wins = 0;
        int draws = 0;

        // Ask for number of players
        int numPlayers = Console.readInt("Enter the number of human players (1-2)", 1, 2);
        if (numPlayers == 2) {
            twoPlayers = true;
        }

        // Game loop
        for (int i = 0; i < numGames; i++) {
            int moveCount = 0;
            printBoard(board);
            while (!gameOver) {
                if (player1Turn) {
                    System.out.println("Player 1's move!");
                    board[playerMove(board) - 1] = 'x';
                } else {
                    System.out.println("Player 2's move!");
                    if (!twoPlayers) {
                        if (moveCount == 0 || (moveCount == 1 && board[4] == '.')) {
                            board[4] = 'o';
                        } else {
                            board[smartComputerMove(board, 'x')] = 'o';
                        }
                    } else {
                        board[playerMove(board) - 1] = 'o';
                    }
                }
                moveCount++;
                gameOver = (checkWinner(board) || moveCount == 9);
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
            board = clearBoard();
        }
    }

    public static int playerMove(char[] board) {
        boolean validMove = false;
        int playerMove = -1;

        while (!validMove) {
            playerMove = Console.readInt("Please enter your move (1-9): ", 1, 9);
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
            compMove = Rng.randInt(0,8);
            if (board[compMove] == '.') {
                validMove = true;
            }
        }

        return compMove;
    }

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

        for (int i = 0; i < 3; i++) {
            System.out.println("-------------");
            for (int j = 0; j < 3; j++) {
                System.out.print("| " + board[3 * i + j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("-------------");
    }

    public static boolean checkWinner(char[] board) {

        // check rows
        for (int i = 0; i < 9; i+=3) {
            if (board[i] == board[i+1] && board[i] == board[i+2] && board[i] != '.') {
                return true;
            }
        }

        // check columns
        for (int i = 0; i < 3; i++) {
            if (board[i] == board[i+3] && board[i] == board[i+6] && board[i] != '.') {
                return true;
            }
        }
        // check diagonals
        if (board[0] == board[4] && board[0] == board[8] && board[0] != '.') {
            return true;
        }

        if (board[2] == board[4] && board[2] == board[6] && board[2] != '.') {
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

    public static char[] clearBoard() {
        char[] newBoard = {'.','.','.','.','.','.','.','.','.'};
        return newBoard;
    }
}
