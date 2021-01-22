package com.tp.rpg.weapons;

import com.tp.rpg.Rng;

public class Fist implements Weapon {
    @Override
    // does 1-3 damage
    public int generateDamage() {
        return Rng.randInt(1,3);
    }
}
