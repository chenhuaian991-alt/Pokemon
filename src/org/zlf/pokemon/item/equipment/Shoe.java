package org.zlf.pokemon.item.equipment;

import org.zlf.pokemon.util.Tools;

import javax.tools.Tool;

public class Shoe extends Equipment{
    public Shoe( int levelNumber) {
        super("鞋子", levelNumber);
        this.attack=0;
        this.defense= Tools.getRandomNumber(10,20,levelNumber);
        this.health=Tools.getRandomNumber(80,100,levelNumber);
    }
}
