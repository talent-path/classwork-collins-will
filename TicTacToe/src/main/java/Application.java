import org.w3c.dom.ls.LSOutput;

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
                        board[computerMove(board)] = 'o';
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

    public static int smartComputerMove(char[] board) {


        return 0;
    }

    public static void printBoard(char[] board) {
        for (int i = 0; i < 9; i++) {
            System.out.print(board[i] + " ");
            if ((i + 1) % 3 == 0) {
                System.out.println();
            }
        }

        System.out.println();
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
