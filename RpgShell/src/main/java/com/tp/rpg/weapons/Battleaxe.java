package com.tp.rpg.weapons;

import com.tp.rpg.Rng;

public class Battleaxe implements Weapon {
    @Override
    // does 5-9 damage
    public int generateDamage() {
        return Rng.randInt(5,9);
    }
}
