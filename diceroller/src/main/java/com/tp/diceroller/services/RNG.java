package com.tp.diceroller.services;

import java.util.Random;

public class RNG {

    static Random rng = new Random();

    public static int rollDice(int numSides) {
        int randomRoll = rng.nextInt(numSides) + 1;

        return randomRoll;
    }
}
