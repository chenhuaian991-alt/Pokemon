package org.zlf.pokemon.item;

import org.zlf.pokemon.DisplayItem;
import org.zlf.pokemon.item.pokemon.*;
import org.zlf.pokemon.util.Tools;
import org.zlf.pokemon.item.equipment.*;
import org.zlf.pokemon.item.HP;
import java.util.Random;

public class Treasure extends Item {
    public Treasure(int levelNumber){
        super("宝箱",levelNumber);
    }

    @Override
    public String getItemInformation() {
        return discover?"۞":"■";
    }
//    可以开出药品、装备或者宠物小精灵、比例为 6 : 3 : 1 ，因为药品、装备、小精灵还未设计，
//    所以暂时先写简单的逻辑实现。
    public Item open(){
        return Tools.getRandomItem(levelNumber);
    }
}
