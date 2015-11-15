package com.game.escape.escapedicision.CustomBase;

import java.io.Serializable;

/**
 * Created by Mhwan on 2015. 11. 11..
 */
public class ItemSoloplayCase implements Serializable{
    private static final long serialVersionUID = 83838681747309791L;
    String input;
    int order;

    public ItemSoloplayCase(String input){
        this.input = input;
    }
    public ItemSoloplayCase(int order, String input){
        this.order = order;
        this.input = input;
    }
    public void setOrder(int order){
        this.order = order;
    }
    public void setInput(String input){
        this.input = input;
    }
    public String getInput(){
        return input;
    }
    public int getOrder(){
        return order;
    }
}
