package com.tp.rpg;

import com.tp.rpg.armors.Shirt;
import com.tp.rpg.weapons.Fist;

// always parries
public class Gambler extends NonPlayerCharacter {

    public Gambler() {
        super(30, new Shirt(), new Fist(), "Gambler", new String[] {"Parry"});
    }
}
