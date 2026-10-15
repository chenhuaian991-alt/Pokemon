package org.zlf.pokemon;

import org.zlf.pokemon.item.HP;
import org.zlf.pokemon.item.Item;
import org.zlf.pokemon.item.Portal;
import org.zlf.pokemon.item.Treasure;
import org.zlf.pokemon.item.equipment.Equipment;
import org.zlf.pokemon.item.monster.Monster;
import org.zlf.pokemon.item.pokemon.Bulbasaur;
import org.zlf.pokemon.item.pokemon.Pokemon;
import org.zlf.pokemon.level.Level;
import org.zlf.pokemon.level.LevelMap;
import org.zlf.pokemon.util.Tools;

import java.util.Arrays;

public class Adventurer implements DisplayItem{
    /**
     * 装备背包
     */
    private Equipment[]equipments={};
    /**
     * 药品背包
     */
    private HP[]medicines={
            new HP(1,10)
    };
    /**
     * 宠物背包
     */
    private Pokemon[]pokemons={
            new Bulbasaur()
    };
    /**
     * 总背包
     */
//    private Item[][] packageItems={
//            equipments,
//            medicines,
//            pokemons
//    };无人读取 做删除处理

    /**
     * 探索给定方向地图位置
     * @param direct 方向
     * @return
     */
    private Item discovery(char direct){
        return (Item)currentLevel.getMap().getPositionItem(Character.toUpperCase(direct));
    }

    /**
     * 向给定方向地图位置移动
     */
    private void move(char direct){
        currentLevel.getMap().move(Character.toUpperCase(direct));
    }

    @Override
    public String getItemInformation() {
        return "♀";
    }

    private Level currentLevel;

    /**
     * 开始闯关
     */
    public void start(){
        currentLevel=new Level(null,1,null);
        LevelMap map=currentLevel.getMap();
        map.addAdventurer(this);
        while(true){
            currentLevel.getMap().show();
            System.out.println("请选择移动方向：W(上)、A(左)、S(下)、D(右)、E(退出)");
            char direct= Tools.getInputChar();
            if(direct=='E'){
                System.out.println("确定要退出吗？ Y/N");
                char quit= Tools.getInputChar();
                if(Character.toUpperCase(quit)=='Y'){
                    System.out.println("感谢使用宠物小精灵闯关");
                    break;
                }
            }else{
                Item item=discovery(direct);//getPositionItem的复杂链式调用的简化
                if(item!=null){
                    //物品被发现
                    item.setDiscover(true);
                    currentLevel.getMap().show();
                }
                if(item instanceof Treasure){
                    processTreasure((Treasure)item,direct );
                } else if (item instanceof Monster) {
                    processMonster((Monster) item,direct);
                }else if(item instanceof Portal){
                    System.out.println("发现传送门，是否通过？ Y/N");
                    char pass=Tools.getInputChar();
                    if(Character.toUpperCase(pass)=='Y'){
                        if(((Portal)item).isNext()){//通往下一关卡的传送门
                            Level nextLevel=currentLevel.getNextLevel();
                            if(nextLevel==null){//下一关卡为空，则需要创建
                                nextLevel=new Level(currentLevel,currentLevel.getNumber()+1,null);
                                //将冒险家加载至地图中
                                nextLevel.getMap().addAdventurer(this);
                                //当前关卡的下一关卡即为新创建的关卡
                                currentLevel.setNextLevel(nextLevel);
                            }
                            currentLevel=nextLevel;
                        }else {//通往上一关卡的传送门
                            Level preLevel=currentLevel.getPrevLevel();
                            if(preLevel==null){
                                System.out.println("非法操作");
                            }else{
                                currentLevel=preLevel;
                            }
                        }
                    }
                }else {
                    move(direct);
                }
            }
        }
    }

    /**
     * 处理怪物
     * @param monster 怪物
     * @param direct 方向
     */
    private void processMonster(Monster monster,char direct){
        System.out.println("发现" +monster.getName() + "，是否清除？ Y/N");
        char clear=Tools.getInputChar();
        if(Character.toUpperCase(clear)=='Y'){
            //选择仍有战斗力的宝可梦出战，全队阵亡则触发方案B：复活全队并传送回第一关
            Pokemon pokemon=selectBattlePokemon();
            if(pokemon==null){
                handleAllDefeated();
                return;
            }
            while(monster.getCurrentHealth()>0&&pokemon.getCurrentHealth()>0){
                //获取宠物小精灵的剩余生命值的比例
                double rate=pokemon.getHealthPercent();
                if(rate<0.5){//生命值低于50%，询问是否使用药品
                    System.out.println(pokemon.getName() + "生命值低于50%，是否使用 药品？ Y/N");
                        char eatHp=Tools.getInputChar();
                        if(Character.toUpperCase(eatHp)=='Y'){
                            HP hp=getCurrentLevelHP(currentLevel.getNumber());
                            if(hp==null){
                                System.out.println("背包中没有可用药品，请探索其他地图");
                            }else{
                                //如果药品可以被销毁，说明没有可用数量
                                if(hp.canDestroy()){
                                    // ① 查找该空药瓶在背包中的索引下标 index
                                    int index = -1;
                                    for(int i=0;i< medicines.length;i++){
                                        if(hp.getLevelNumber()==medicines[i].getLevelNumber()){
                                            index=i;
                                            break;
                                        }
                                    }
                                    // 找到 index 后，用 copyOf 去掉该元素
                                    HP[]tmp=new HP[medicines.length-1];
                                    System.arraycopy(medicines,0,tmp,0,index);
                                    System.arraycopy(medicines,index+1,tmp,index,medicines.length-1-index);
                                    medicines=tmp;
                                    System.out.println("药品已经使用完毕");
                                }else{
                                    int health=hp.use();
                                    pokemon.setCurrentHealth(pokemon.getCurrentHealth()+health);
                                }
                            }
                        }
                }
                Tools.lazy(300L);
                pokemon.attackMonster(monster);
                Tools.lazy(300L);
                monster.attackPokemon(pokemon);
                Tools.lazy(300L);
            }
            //有一个数据为0了 退出while
            if(monster.getCurrentHealth()==0){
                System.out.println("怪物已被击败");
                //怪物掉落物品
                Item dropItem=monster.drop();
                //展示获取的物品信息
                System.out.println("怪物已被击败，掉落" + dropItem.getItemInformation());
                processItem(dropItem);
                //怪物被击败后
                move(direct);
            }else{
                System.out.println(pokemon.getName() + "已被击败");
                monster.resume();
            }
        }
    }



    /**
     * 选择仍有战斗力的宝可梦出战，自动过滤已阵亡的个体（方案B）
     * @return 出战的宝可梦，若全队阵亡返回 null
     */
    private Pokemon selectBattlePokemon(){
        //只列出当前生命值大于0的宝可梦
        int aliveCount=0;
        for(int i=0;i<pokemons.length;i++){
            if(pokemons[i].getCurrentHealth()>0){
                aliveCount++;
                System.out.println(aliveCount + "\t" + pokemons[i].getItemInformation()
                        + "（当前生命值=" + pokemons[i].getCurrentHealth() + "/" + pokemons[i].getHealth() + "）");
            }
        }
        if(aliveCount==0){//全队阵亡，交由调用方触发全灭事件
            return null;
        }
        System.out.println("请选择出战宠物小精灵：");
        int number=Tools.getInputNumber(1,aliveCount);
        //将列表序号映射回宠物背包中的实际下标
        int aliveIndex=0;
        for(int i=0;i<pokemons.length;i++){
            if(pokemons[i].getCurrentHealth()>0){
                aliveIndex++;//i=0对应index为1;
                if(aliveIndex==number){
                    return pokemons[i];
                }
            }
        }
        return null;//输入范围已被 getInputNumber 校验，实际不会执行到这里
    }

    /**
     * 全灭事件（方案B）：复活所有宝可梦，若不在第一关则沿关卡链传送回第一关
     */
    private void handleAllDefeated(){
        boolean needBack=currentLevel.getPrevLevel()!=null;
        //全队复活并回满生命值
        for(int i=0;i<pokemons.length;i++){
            pokemons[i].setCurrentHealth(pokemons[i].getHealth());
        }
        //沿 prevLevel 链回溯至第一关
        while(currentLevel.getPrevLevel()!=null){
            currentLevel=currentLevel.getPrevLevel();
        }
        if(needBack){
            System.out.println("所有宠物小精灵均已阵亡，被传送回第一关并全部复活");
        }else{
            System.out.println("所有宠物小精灵均已阵亡，已全部复活");
        }
        Tools.lazy(300L);
    }

    /**
     * 获取当前关卡使用的药品，如果当前关卡的药品已经使用完，那么可以使用上一关卡的药品，依次类
     推
     * @param levelNumber
     * @return
     */
    private HP getCurrentLevelHP(int levelNumber){
        if(levelNumber==0)return null;
        HP hp=null;
        for(int i=0;i< medicines.length;i++){
            if(medicines[i].getLevelNumber()==levelNumber){
                hp=medicines[i];
                break;
            }
        }
        if(hp==null){
            return getCurrentLevelHP(levelNumber-1);
        }else {
            return hp;
        }
    }

    /**
     * 处理获得物品
     * @param item
     */
    private void processItem(Item item){
        if(item instanceof HP){
            boolean merged=false;
            for(HP hp:medicines){
                if(hp.getLevelNumber()==item.getLevelNumber()){
                    hp.addCount(((HP)item).getCount());
                    merged=true;
                    break;
                }
            }
            if(!merged){
                medicines=Arrays.copyOf(medicines,medicines.length+1);
                medicines[medicines.length-1]=(HP)item;
            }
        } else if (item instanceof Equipment) {
            System.out.println("发现新的装备，是否给宠物小精灵更换？ Y/N");
            char change=Tools.getInputChar();
            if(Character.toUpperCase(change)=='Y'){
                Equipment current=(Equipment) item;
                for(Pokemon pokemon:pokemons){
                    // 当前宝可梦尝试穿戴 current，并将淘汰下来的旧装备重新赋给 current
                    current=pokemon.changeEquipment(current);
                    // 如果没有淘汰下任何装备（说明直接穿上了空槽位），后续小精灵无需再看
                    if(current==null)break;
                }
                //如果换下来的旧装备不为空，直接放入背包中
                if(current!=null){
                    equipments= Arrays.copyOf(equipments,equipments.length+1);
                    equipments[equipments.length-1]=current;
                    //current 仍是新装备本身，说明没有宝可梦穿上它
                    if(current==item){
                        System.out.println("没有宝可梦能穿戴" + item.getItemInformation() + "，已放入背包");
                    }else{
                        System.out.println("更换完成，换下的" + current.getItemInformation() + " 已放入背包");
                    }
                }else{
                    System.out.println(item.getItemInformation() + " 穿戴成功");
                }
            }else{
                //不更换也不能让装备凭空消失，放入背包
                equipments=Arrays.copyOf(equipments,equipments.length+1);
                equipments[equipments.length-1]=(Equipment) item;
                System.out.println(item.getItemInformation() + " 已放入背包");
            }
        }else{//宠物小精灵
            int index=-1;
            for(int i=0;i<pokemons.length;i++){
                if(item.getClass()==pokemons[i].getClass()){
                    index=i;
                    break;
                }
            }
            if(index==-1){
                pokemons=Arrays.copyOf(pokemons,pokemons.length+1);
                pokemons[pokemons.length-1]=(Pokemon) item;
            }else{//存在同类型宠物小精灵
                System.out.println("发现可融合宠物小精灵，是否融合？ Y/N");
                char merge=Tools.getInputChar();
                if(Character.toUpperCase(merge)=='Y'){
                    pokemons[index].merge((Pokemon) item);
                }else{
                    pokemons=Arrays.copyOf(pokemons,pokemons.length+1);
                    pokemons[pokemons.length-1]=(Pokemon) item;
                }
            }
        }
    }

    /**
     * 处理宝箱
     * @param treasure 宝箱
     */
    private void processTreasure(Treasure treasure,char direct){
        System.out.println("发现宝箱，是否打开？ Y/N");
        char open = Tools.getInputChar();
        if(Character.toUpperCase(open)=='Y'){
            //开启宝箱获得一个物品
            Item item=treasure.open();
            //展示获取的物品信息
            System.out.println("获得" + item.getItemInformation());
            processItem(item);
            //宝箱处理后，冒险家移动至宝箱的位置
            move(direct);
        }
    }



}
