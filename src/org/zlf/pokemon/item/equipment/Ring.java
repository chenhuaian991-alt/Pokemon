package org.zlf.pokemon.item.equipment;

import org.zlf.pokemon.util.Tools;

public class Ring extends Equipment{
    public Ring( int levelNumber) {
        super("项链", levelNumber);
        this.attack= Tools.getRandomNumber(20,30,levelNumber);
        this.defense=Tools.getRandomNumber(20,20,levelNumber);
        this.health=Tools.getRandomNumber(100,200,levelNumber);
    }
}
