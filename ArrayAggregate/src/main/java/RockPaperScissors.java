import java.util.Scanner;

public class RockPaperScissors {
    public static void main(String[] args) {
        int player = getPlayerChoice();
        int comp = getComputerChoice();
        printChoice(player, comp);

    }

    public static int getPlayerChoice() {
        Scanner input = new Scanner(System.in);
        int player = -1;
        boolean valid = false;

        while (!valid) {
            System.out.println("ENTER 1 FOR ROCK, 2 FOR PAPER, 3 FOR SCISSORS");
            try {
                player = input.nextInt();
                valid = true;
            } catch (NumberFormatException ex) {

            }
        }

        return player;
    }

    public static int getComputerChoice() {
        return Rng.randInt(1, 3);
    }

    public static void printChoice(int player, int computer) {
        String[] moves = {"ROCK", "PAPER", "SCISSORS"};

        System.out.println("You have selected " + moves[player - 1]);
        System.out.println("Computer has selected " + moves[computer - 1]);

        System.out.println(player);
        System.out.println(computer);

        System.out.println((player + 1) % 3 + 1);

        if (player == computer) {
            System.out.println("It's a tie!");
        } else if ((player + 1) % 3 + 1 != computer) {
            System.out.println("You lose!");
        } else {
            System.out.println("You win!");
        }
    }
}
