package com.tp.rpg.weapons;

import com.tp.rpg.Rng;

public class Dagger implements Weapon {
    @Override
    // does 3-5 damage
    public int generateDamage() {
        return Rng.randInt(3,5);
    }
}
