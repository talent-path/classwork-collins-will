package com.tp.rpg;

import com.tp.rpg.armors.*;
import com.tp.rpg.weapons.*;

public class PlayerCharacter extends Character {

    int potions = 1;

    public PlayerCharacter(int hp, Armor armor, Weapon weapon, String name) {
        super(hp, armor, weapon, name);
    }

    //use scanner here to get something from the user
    @Override
    public String makeChoice() {
        int max = 3;
        String choiceText = "Attack (1)\nDefend (2)\nParry (3)\n";
        if (potions > 0) {
            choiceText += "Potion x" + this.potions + " (4)";
            max = 4;
        }
        int choice = Console.readInt(choiceText, 1, 4);
        switch (choice) {
            case 1:
                return "Attack";
            case 2:
                return "Defend";
            case 3:
                return "Parry";
            case 4:
                return "Potion";
            default:
                return "ERROR";
        }
    }
}
