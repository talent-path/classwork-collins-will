package com.tp.rpg;

import com.tp.rpg.armors.Armor;
import com.tp.rpg.weapons.Weapon;

//TODO:
//      add a concept of hitpoints
//      add a concept of armor (or maybe multiple pieces of armor)
//      add a concept of a weapon (or maybe multiple weapons)
//Stretch goals:
//      add a potion class/interface that the character can drink instead of attacking
//      let the character "disarm" the opponent instead of attacking
//          base this on the weapons used?
//      let the character choose to "block" or "defend"
//          could stun the opponent if they attack?
//          could give us TWO attacks on the next round?
public abstract class Character implements Chooser {

    //TODO: add fields for armor(s) and weapon(s)

    int hp;
    int maxHP;
    int potions = 1;
    Armor armor;
    Weapon weapon;
    String name;

    public Character(int hp, Armor armor, Weapon weapon, String name) {
        this.hp = hp;
        this.maxHP = hp;
        this.armor = armor;
        this.weapon = weapon;
        this.name = name;
    }

    public void attack( Character defender, int modifier ){
        int initialDamage = this.weapon.generateDamage();
        defender.takeDamage(defender.armor.reduceDamage(initialDamage) + modifier);
    }

    // used when a specific amount of damage is already known
    public void attack (Character defender, int modifier, int damage) {
        defender.takeDamage(defender.armor.reduceDamage(damage) + modifier);
    }

    // 40% chance to reflect all incoming damage back to attacker
    // otherwise, take full damage
    public void parry(int incomingDamage, Character attacker) {
        double diceRoll = Rng.randDouble(0, 1);
        if (diceRoll >= 0.4) {
            // success
            System.out.println("Successful parry!");
            attacker.takeDamage(incomingDamage);
        } else {
            // failure
            System.out.println("Failed parry!");
            this.takeDamage(incomingDamage);
        }
    }

    public void takeDamage( int damage ){
        int actualDamage = damage < 0 ? 0 : damage;
        this.hp -= damage;
    }

    public boolean isAlive(){
        return this.hp > 0;
    }

    public void usePotion() {
        if (this.potions > 0) {
            int oldHp = this.hp;
            potions--;
            // potions heal 2d6 damage
            this.hp += Rng.randInt(1,6) + Rng.randInt(1,6);
            if (this.hp > this.maxHP) {
                this.hp = this.maxHP;
            }
            System.out.println("You healed " + (this.hp - oldHp) + " HP");
        } else {
            System.out.println("Out of potions");
        }
    }

}
