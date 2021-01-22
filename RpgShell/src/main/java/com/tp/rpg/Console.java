package com.tp.rpg;

import java.util.Scanner;

public class Console {

    public static void print(String msg) {
        System.out.print(msg);
    }
    public static float readFloat(String msg)
    {
        boolean isValid = false;
        float parsedFloat = Float.MIN_VALUE;
        return parsedFloat;
    }
    public static double readDouble(String msg, double min, double max) {
        boolean isValid = false;
        double value = Double.MIN_VALUE;
        while (!isValid) {
            value = readDouble(msg);
            if (value >= min && value <= max) {
                System.out.println("Valid Double!");
                isValid = true;
            } else
                System.out.println("Not correct!!! Try again.");
        }
        return value;
    }
    public static double readDouble(String msg) {
        Scanner scan = new Scanner(System.in);
        boolean isValid = false;
        double parsedDouble = Double.MIN_VALUE;
        while (!isValid) {
            print(msg);
            String userInput = scan.nextLine();
            try {
                parsedDouble = Double.parseDouble(userInput);
                isValid = true;
            } catch (NumberFormatException ex) {
            }
        }
        return parsedDouble;
    }
    public static int readInt(String msg, int min, int max) {
        boolean isValid = false;
        int value = Integer.MIN_VALUE;
        while (!isValid) {
            value = readInt(msg);
            if (value >= min && value <= max) {
                isValid = true;
            } else {
                System.out.println("Please input number between min & max.");
            }
        }
        return value;
    }
    public static int readInt(String msg) {
        Scanner scan = new Scanner(System.in);
        boolean isValid = false;
        int parsedInt = Integer.MIN_VALUE;
        while (!isValid) {
            print(msg);
            String userInput = scan.nextLine();
            try {
                parsedInt = Integer.parseInt(userInput);
                isValid = true;
            } catch (NumberFormatException ex) {
            }
        }
        return parsedInt;
    }

}
