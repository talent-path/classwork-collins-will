package com.tp.rpg.armors;

public interface Armor {

    int bonusWhileDefending = 0;

    //takes in an amount of damage dealt
    //outputs the amount of damage to actually take
    int reduceDamage( int startingDamage );

}
