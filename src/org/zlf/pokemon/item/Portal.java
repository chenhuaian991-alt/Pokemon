package org.zlf.pokemon.item;

import org.zlf.pokemon.DisplayItem;

public class Portal extends Item {
    /**
     * 是否是通往下一关卡的传送门
     */
    private boolean next;

    public Portal(boolean next){
        super("传送门");
        this.next=next;
    }

    public boolean isNext(){
        return next;
    }

    @Override
    public String getItemInformation() {
        if(discover){
            //单字符箭头，避免 "->" 两字符宽破坏地图行对齐
            return next?"→":"←";//一个下一关 一个上一关
        }
        return "■";
    }
}
