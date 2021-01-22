package com.tp.rpg;

import com.tp.rpg.armors.*;
import com.tp.rpg.weapons.*;

public class PlayerCharacter extends Character {

    public PlayerCharacter(int hp, Armor armor, Weapon weapon, String name) {
        super(hp, armor, weapon, name);
    }

    //use scanner here to get something from the user
    @Override
    public String makeChoice() {
        int choice = Console.readInt("Attack (1), Defend (2), or Parry (3): ", 1, 3);
        switch (choice) {
            case 1:
                return "Attack";
            case 2:
                return "Defend";
            case 3:
                return "Parry";
            default:
                return "ERROR";
        }
    }
}
