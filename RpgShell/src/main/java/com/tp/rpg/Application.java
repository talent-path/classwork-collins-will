package com.tp.rpg;
import com.tp.rpg.armors.*;
import com.tp.rpg.weapons.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {

        PlayerCharacter pc = setUpPlayer();
        int battlesWon = 0;
        int weaponUpgrades = 0;
        int armorUpgrades = 0;
        int healthUpgrades = 0;

        Weapon[] weaponList = {new Dagger(), new Battleaxe()};
        Armor[] armorList = {new LeatherArmor(), new PlateArmor()};

        while( pc.isAlive() ){
            // list of all enemy types
            // as the player wins more battles, more enemies will be unlocked
            NonPlayerCharacter[] bestiary = {new Pacifist(), new Goblin(), new Monk(), new Warrior()};
            NonPlayerCharacter enemy = setUpEnemy(bestiary, battlesWon);

            if(battle( pc, enemy )) {
                battlesWon++;

                // Level up
                boolean choiceMade = false;

                while (!choiceMade) {
                    int choice = Console.readInt("Choose to upgrade weapon (1), armor (2), or health (3): ", 1, 3);
                    switch (choice) {
                        case 1:
                            if (weaponUpgrades < weaponList.length) {
                                choiceMade = true;
                                pc.weapon = weaponList[weaponUpgrades];
                                weaponUpgrades++;
                            } else {
                                System.out.println("Max weapon tier already reached");
                            }
                            break;
                        case 2:
                            if (armorUpgrades < armorList.length) {
                                choiceMade = true;
                                pc.armor = armorList[armorUpgrades];
                                armorUpgrades++;
                            } else {
                                System.out.println("Max armor tier already reached");
                            }
                            break;
                        case 3:
                            choiceMade = true;
                            healthUpgrades++;
                            pc.hp = 20 + 10 * healthUpgrades;
                            break;
                    }
                }
            }
        }

        gameOverScreen(battlesWon);

    }

    //walk the user through setting up their character
    private static PlayerCharacter setUpPlayer() {
        System.out.println("Welcome to the arena!");
        Scanner scan = new Scanner(System.in);
        System.out.print("Please enter your name: ");
        String playerName = scan.nextLine();
        return new PlayerCharacter(20, new Shirt(), new Fist(), playerName);
    }

    //create some NPC object (with armor & weapons?)
    private static NonPlayerCharacter setUpEnemy(NonPlayerCharacter[] enemyList, int wins) {
        int enemyChoice = wins >= enemyList.length ? Rng.randInt(0, enemyList.length - 1) : wins;
        return enemyList[enemyChoice];
    }

    //a and b battle until one is dead
    // Simultaneous turn system (both characters choose a move and both play out in a single turn)
    // return if a won
    private static boolean battle(Character a, Character b) {

        System.out.println(a.name + " VS " + b.name);

        while( a.isAlive() && b.isAlive() ){
            String aChoice = a.makeChoice();
            String bChoice = b.makeChoice();

            System.out.println("Player choice: " + aChoice);
            System.out.println("Enemy choice: " + bChoice);

            int oldAHP = a.hp;
            int oldBHP = b.hp;

            if (aChoice.equals("Attack")) {
                System.out.println(a.name + " attacks!");
                if (bChoice.equals("Defend")) {
                    System.out.println(b.name + " defends!");
                    a.attack(b, -b.armor.bonusWhileDefending);
                } else if (bChoice.equals("Parry")) {
                    System.out.println(b.name + " tries to parry...");
                    b.parry(a.weapon.generateDamage(), a);
                } else {
                    a.attack(b, 0);
                }
            }
            if (bChoice.equals("Attack") && b.isAlive()) {
                System.out.println(b.name + " attacks!");
                if (aChoice.equals("Defend")) {
                    System.out.println(a.name + " defends!");
                    b.attack(a, -a.armor.bonusWhileDefending);
                } else if (aChoice.equals("Parry")) {
                    System.out.println(a.name + " tries to parry...");
                    a.parry(b.weapon.generateDamage(), b);
                } else {
                    b.attack(a, 0);
                }
            }
            if (!aChoice.equals("Attack") && !bChoice.equals("Attack")) {
                System.out.println("Both fighters are circling, looking for an opening...");
            }

            System.out.println(a.name + " takes " + (oldAHP - a.hp) + " damage");
            System.out.println(b.name + " takes " + (oldBHP - b.hp) + " damage");

            //TODO: display HP status?
            System.out.println("Player HP: " + a.hp);
            System.out.println("Enemy HP: " + b.hp);

            if (b.hp <= 0) {
                System.out.println("Enemy defeated!");
            }
        }
        return a.hp > 0;
    }

    //display some message
    private static void gameOverScreen(int wins) {
        System.out.println("You have fallen...");
        System.out.print("You defeated " + wins + " enem");
        String end = wins == 1 ? "y" : "ies";
        System.out.println(end);
    }
}
