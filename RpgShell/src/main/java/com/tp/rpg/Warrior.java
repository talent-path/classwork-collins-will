package com.tp.rpg;

import com.tp.rpg.armors.Shirt;
import com.tp.rpg.weapons.Fist;

public class Warrior extends NonPlayerCharacter {

    public Warrior() {
        super(40, new Shirt(), new Fist(), "Warrior", new String[] {"Attack", "Defend", "Parry"});
    }
}
