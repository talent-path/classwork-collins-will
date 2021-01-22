package com.tp.rpg;

import com.tp.rpg.armors.Shirt;
import com.tp.rpg.weapons.Fist;

// always parries
public class Monk extends NonPlayerCharacter {

    public Monk() {
        super(30, new Shirt(), new Fist(), "Gambler", new String[] {"Parry"});
    }
}
