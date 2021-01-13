import java.util.Scanner;

public class Console {

    public static void print(String print) {
        System.out.println(print);
    }

    public static int readInt(String msg, int min, int max) {
        boolean inRange = false;
        int parsedInt = Integer.MIN_VALUE;

        while (!inRange) {
            parsedInt = readInt(msg);
            if (parsedInt >= min && parsedInt <= max) {
                inRange = true;
            }
        }

        return parsedInt;
    }

    public static int readInt(String msg) {
        Scanner input = new Scanner(System.in);
        int parsedInt = Integer.MIN_VALUE;
        boolean validInput = false;

        while(!validInput) {
            print(msg);
            String userInput = input.nextLine();
            try {
                parsedInt = Integer.parseInt(userInput);
                validInput = true;
            } catch(NumberFormatException ex) {

            }
        }
        return parsedInt;
    }

    public static double readDouble(String msg, double min, double max) {
        boolean inRange = false;
        double parsedDouble = Double.MIN_VALUE;

        while (!inRange) {
            parsedDouble = readDouble(msg);
            if (parsedDouble >= min && parsedDouble <= max) {
                inRange = true;
            }
        }

        return parsedDouble;
    }

    public static double readDouble(String msg) {
        Scanner input = new Scanner(System.in);
        double parsedDouble = Double.MIN_VALUE;
        boolean validInput = false;

        while (!validInput) {
            print(msg);
            String userInput = input.nextLine();
            try {
                parsedDouble = Double.parseDouble(userInput);
                validInput = true;
            } catch (NumberFormatException ex) {

            }
        }

        return parsedDouble;
    }
}
