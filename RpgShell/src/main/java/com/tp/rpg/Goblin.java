package com.tp.rpg;
import com.tp.rpg.armors.Shirt;
import com.tp.rpg.weapons.Dagger;

//goblins always attack
public class Goblin extends NonPlayerCharacter {

    public Goblin() {
        super(15, new Shirt(), new Dagger(),"Goblin", new String[] {"Attack"});
    }
}
