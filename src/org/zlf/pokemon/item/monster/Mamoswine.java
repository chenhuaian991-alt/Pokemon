package org.zlf.pokemon.item.monster;

import org.zlf.pokemon.util.Tools;

public class Mamoswine extends Monster{
    public Mamoswine(int levelNumber) {
        super("象牙猪", levelNumber);
        this.attack = Tools.getRandomNumber(45, 55, levelNumber);
        this.defense = Tools.getRandomNumber(35, 45, levelNumber);
        this.health = Tools.getRandomNumber(600, 800, levelNumber);
        this.currentHealth = this.health;
    }

    @Override
    public String getItemInformation() {
        return discover ? "A" : "■";
    }
}
