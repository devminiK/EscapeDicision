package com.game.escape.escapedicision.CustomBase;

/**
 * Created by Mhwan on 2015. 12. 5..
 */
public class ItemFavoriteList {
    int id;
    String text;
    int activity_num;
    public ItemFavoriteList(){

    }
    public ItemFavoriteList(int id, String text){
        this.id = id;
        this.text = text;
    }
    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getActivity_num(){
        return activity_num;
    }
    public void setText(String text){
        this.text = text;
    }
    public void setActivity_num(int num){
        this.activity_num = num;
    }
    public String getText(){
        return this.text;
    }
}
