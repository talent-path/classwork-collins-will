package com.tp.rpg.armors;

import com.tp.rpg.Rng;

public class PlateArmor implements Armor {
    int bonusWhileDefending = 5;

    @Override
    public int reduceDamage(int startingDamage) {
        return startingDamage - Rng.randInt(3,6);
    }
}
