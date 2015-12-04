package com.game.escape.escapedicision.CustomBase;

/**
 * Created by Mhwan on 2015. 12. 5..
 */
public class ItemFavoriteList {
    int id;
    String text;
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
    public void setText(String text){
        this.text = text;
    }
    public String getText(){
        return this.text;
    }
}
