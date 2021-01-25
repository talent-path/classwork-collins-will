package com.tp.rpg;

import com.tp.rpg.armors.Shirt;
import com.tp.rpg.weapons.Fist;

// always parries
public class Monk extends NonPlayerCharacter {

    public Monk() {
        super(20, new Shirt(), new Fist(), "Monk", new String[] {"Parry"});
    }
}
