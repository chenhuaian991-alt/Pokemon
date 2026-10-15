package org.zlf.pokemon.item.equipment;

/**
 * GM神装：各类型按随机区间上限的满属性装备（仅供调试菜单使用）
 */
public class GodEquipment {

    /**
     * @return 一整套8件满属性装备：头盔、铠甲、护腿、鞋子、武器、项链、戒指、手镯
     */
    public static Equipment[] fullSet(){
        Helmet helmet=new Helmet(1);
        helmet.defense=30;
        helmet.health=150;
        Armor armor=new Armor(1);
        armor.defense=50;
        armor.health=250;
        Leggings leggings=new Leggings(1);
        leggings.defense=40;
        leggings.health=200;
        Shoe shoe=new Shoe(1);
        shoe.defense=20;
        shoe.health=100;
        Weapon weapon=new Weapon(1);
        weapon.attack=150;
        weapon.health=300;
        Necklace necklace=new Necklace(1);
        necklace.attack=35;
        necklace.defense=25;
        necklace.health=180;
        Ring ring=new Ring(1);
        ring.attack=30;
        ring.defense=20;
        ring.health=200;
        Bracelet bracelet=new Bracelet(1);
        bracelet.attack=30;
        bracelet.defense=20;
        bracelet.health=200;
        return new Equipment[]{helmet,armor,leggings,shoe,weapon,necklace,ring,bracelet};
    }
}
