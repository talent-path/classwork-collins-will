package com.tp.rpg;

import com.tp.rpg.armors.*;
import com.tp.rpg.weapons.*;

public abstract class NonPlayerCharacter extends Character {

    // Represents an NPC possible moves and their probabilities
    // Ex: A goblin's move deck would be ["Attack"]
    String[] moveDeck;

    public NonPlayerCharacter(int hp, Armor armor, Weapon weapon, String type, String[] moveDeck) {
        super(hp, armor, weapon, type);
        this.moveDeck = moveDeck;
    }

    @Override
    public String makeChoice() {
        return moveDeck[Rng.randInt(0, moveDeck.length - 1)];
    }
}
