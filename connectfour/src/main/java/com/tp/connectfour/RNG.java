package com.tp.connectfour;

import java.util.Random;
// Singleton pattern
// single static instance of something inside a class
// which we then use throughout
public class RNG {
    static Random rng = new Random();
    public static int randInt(int incMin, int incMax) {
        // this call takes an exclusive upper bound (or max)
        // returns a number between 0 and that bound - 1
        // rng.nextInt();
        int rand = incMin + rng.nextInt(incMax - incMin + 1);
        return rand;
    }
    public static double randDouble(double incMin, double incMax) {
        double rand = incMin + rng.nextDouble() * (incMax - incMin);
        return rand;
    }
    public static boolean coinFlip() {
        return rng.nextBoolean();
    }
}