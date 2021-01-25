package com.tp.rpg;

import com.tp.rpg.armors.*;
import com.tp.rpg.weapons.*;

public class PlayerCharacter extends Character {

    int potions = 1;
    int weaponUpgrades = 0;
    int armorUpgrades = 0;
    int healthUpgrades = 0;

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

    public void levelUp(Weapon[] weaponList, Armor[] armorList) {
        boolean choiceMade = false;

        while (!choiceMade) {
            int choice = Console.readInt("Choose to upgrade weapon (1), armor (2), or health (3): ", 1, 3);
            switch (choice) {
                case 1:
                    if (this.weaponUpgrades < weaponList.length) {
                        choiceMade = true;
                        this.weapon = weaponList[this.weaponUpgrades];
                        this.weaponUpgrades++;
                    } else {
                        System.out.println("Max weapon tier already reached");
                    }
                    break;
                case 2:
                    if (this.armorUpgrades < armorList.length) {
                        choiceMade = true;
                        this.armor = armorList[this.armorUpgrades];
                        this.armorUpgrades++;
                    } else {
                        System.out.println("Max armor tier already reached");
                    }
                    break;
                case 3:
                    choiceMade = true;
                    this.healthUpgrades++;
                    this.maxHP = 20 + 10 * this.healthUpgrades;
                    this.hp = this.maxHP;
                    break;
            }
        }
    }
}
