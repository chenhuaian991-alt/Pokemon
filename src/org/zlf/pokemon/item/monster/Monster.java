package org.zlf.pokemon.item.monster;

import org.zlf.pokemon.item.Item;
import org.zlf.pokemon.item.pokemon.Pokemon;
import org.zlf.pokemon.util.Tools;

public abstract class Monster extends Item {
    public Monster(String name, int levelNumber) {
        super(name, levelNumber);
    }
    /**
     * 攻击力
     */
    protected int attack;
    /**
     * 防御力
     */
    protected int defense;
    /**
     * 生命值
     */
    protected int health;
    /**
     * 怪物当前血量
     */
    protected int currentHealth;

    public int getDefense(){
        return defense;
    }

    public int getCurrentHealth(){
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth){
        this.currentHealth=currentHealth;
    }

    /**
     * 攻击宠物小精灵
     * @param pokemon 宠物小精灵
     */
    public void attackPokemon(Pokemon pokemon){
        int minusHealth=this.attack*this.attack/ pokemon.getDefense();
        if(minusHealth==0){
            minusHealth=1;
        } else if (minusHealth>= pokemon.getCurrentHealth()) {
            minusHealth= pokemon.getCurrentHealth(); //伤害就应该等于宠物小精灵当前血量
        }
        //剩余血量
        int restHealth= pokemon.getCurrentHealth()-minusHealth;
        pokemon.setCurrentHealth(restHealth);

        pokemon.setHealth(pokemon.getHealth()-minusHealth);
        // 红色输出怪物攻击，输出顺序稳定且不占用错误流
        System.out.println("\033[31m" + name + "对" + pokemon.getName() + "发动攻击,造成了" + minusHealth + "伤害\033[0m");
    }

    /**
     * 怪物掉落装备
     * @return
     */
    public Item drop(){
        return Tools.getRandomItem(levelNumber);
    }

    /**
     * 怪物恢复
     */
    public void resume(){
        currentHealth=health;
    }

    @Override
    public String getItemInformation() {
        return discover?name:"■";
    }
}
