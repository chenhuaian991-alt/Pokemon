package org.zlf.pokemon.item;

import org.zlf.pokemon.DisplayItem;
//抽象类（Abstract Class） 实现接口：Java 允许抽象类不对接口方法做具体实现。
//此时，接口中的抽象方法会被隐式继承到抽象类中，相当于抽象类内部自动声明了
//债留子类：抽象类自己可以不实现，但后续继承它的非抽象具体子类（如 Portal、Treasure 等）
//，必须把这个“欠下的方法”全部重写实现，否则子类自己也无法通过编译
public abstract class Item implements DisplayItem {
    /**
     * 物品名称
     */
    protected String name;
    /**
     * 关卡编号
     */
    protected int levelNumber;
    /**
     * 是否被探索
     */
    protected boolean discover;

    public Item(String name){
        this.name=name;
    }

    public Item(String name, int levelNumber) {
        this.name = name;
        this.levelNumber = levelNumber;
    }

    public void setDiscover(boolean discover){
        this.discover=discover;
    }

    public String getName(){
        return name;
    }

    public int getLevelNumber() {
        return levelNumber;
    }
}
