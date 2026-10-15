package org.zlf.pokemon.item.equipment;

import org.zlf.pokemon.util.Tools;

public class Weapon extends Equipment{
    public Weapon(int levelNumber) {
        super("武器", levelNumber);
        this.attack= Tools.getRandomNumber(100,150,levelNumber);
        this.defense=0;
        this.health= Tools.getRandomNumber(250,300,levelNumber);
    }
}
