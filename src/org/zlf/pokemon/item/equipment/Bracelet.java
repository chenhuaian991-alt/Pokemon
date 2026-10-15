package org.zlf.pokemon.item.equipment;

import org.zlf.pokemon.util.Tools;

import javax.tools.Tool;

public class Bracelet extends Equipment{
    public Bracelet( int levelNumber) {
        super("手镯", levelNumber);
        this.attack= Tools.getRandomNumber(20,30,levelNumber);
        this.defense= Tools.getRandomNumber(20,20,levelNumber);
        this.health=Tools.getRandomNumber(100,200,levelNumber);
    }
}
