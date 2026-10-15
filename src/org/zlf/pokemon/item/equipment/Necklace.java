package org.zlf.pokemon.item.equipment;

import org.zlf.pokemon.util.Tools;

import javax.tools.Tool;

public class Necklace extends Equipment{
    public Necklace(int levelNumber) {
        super("项链", levelNumber);
        this.attack= Tools.getRandomNumber(25,35,levelNumber);
        this.defense=Tools.getRandomNumber(25,25,levelNumber);
        this.health=Tools.getRandomNumber(120,180,levelNumber);
    }
}
