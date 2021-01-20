import java.util.Scanner;

public class Console {
    static Scanner scn = new Scanner( System.in );

    public static void print( String msg ){
        System.out.print(msg);
    }

    public static void print( char msg ){
        System.out.print(msg);
    }

    public static int readInt( String prompt ){
        int toReturn = Integer.MIN_VALUE;



        boolean valid = false;
        while( !valid ){
            print( prompt );
            String userInput = scn.nextLine();

            try{
                toReturn = Integer.parseInt(userInput);
                valid = true;
            } catch( NumberFormatException ex ){
                //do nothing because the user messing up like this is normal
            }
        }

        return toReturn;
    }

    public static int readInt( String prompt, int min, int max ){
        int toReturn = Integer.MIN_VALUE;

        boolean valid = false;
        while( !valid ){
            toReturn = readInt(prompt);

            valid = toReturn <= max && toReturn >= min;

        }

        return  toReturn;
    }
}
