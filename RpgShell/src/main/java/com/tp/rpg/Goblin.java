package com.tp.rpg;
import com.tp.rpg.armors.Shirt;
import com.tp.rpg.weapons.Fist;

//goblins always attack
public class Goblin extends NonPlayerCharacter {

    public Goblin() {
        super(20, new Shirt(), new Fist(),"Goblin", new String[] {"Attack"});
    }
}
