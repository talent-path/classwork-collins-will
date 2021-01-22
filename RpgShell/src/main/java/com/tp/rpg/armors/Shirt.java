package com.tp.rpg.armors;

import com.tp.rpg.Rng;

public class Shirt implements Armor  {
    // When choosing defend, 1 more damage will be reduced
    int bonusWhileDefending = 1;

    @Override
    // reduces 0-1 damage
    public int reduceDamage(int startingDamage) {
        return startingDamage - Rng.randInt(0,1);
    }
}
