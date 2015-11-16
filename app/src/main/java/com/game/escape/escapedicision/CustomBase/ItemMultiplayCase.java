package com.game.escape.escapedicision.CustomBase;

import java.io.Serializable;

/**
 * Created by Mhwan on 2015. 11. 17..
 */
public class ItemMultiplayCase implements Serializable{
    private static final long serialVersionUID = 7111406953535745624L;
    private int order;
    private String input_1, input_2;

    public void setOrder(int order){
        this.order = order;
    }
    public void setInput_1(String input){
        this.input_1 = input;
    }
    public String getInput_1(){
        return input_1;
    }
    public void setInput_2(String input){
        this.input_2 = input;
    }
    public String getInput_2(){
        return input_2;
    }
    public int getOrder(){
        return order;
    }
}
