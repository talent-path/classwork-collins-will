package com.tp.rpg;

import com.tp.rpg.armors.*;
import com.tp.rpg.weapons.*;

public class Pacifist extends NonPlayerCharacter {

    public Pacifist() {
        super(10, new Shirt(), new Fist(), "Pacifist", new String[] {"Defend"});
    }
}
