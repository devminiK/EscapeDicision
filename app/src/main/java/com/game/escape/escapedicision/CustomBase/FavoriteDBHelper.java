package com.game.escape.escapedicision.CustomBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mhwan on 2015. 12. 5..
 */
public class FavoriteDBHelper extends SQLiteOpenHelper {
    static String DATABASE_NAME="favoritedata";
    public static final String TABLE_NAME="favorite";
    public static final String KEY_FAVORITE_TEXT="ftext";
    public static final String KEY_ID="id";
    public static final String KEY_ACNUM = "activity";
    public static final String SEPERATOR_FAVORITE = ", ";

    public FavoriteDBHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+" ("+KEY_ID+" INTEGER PRIMARY KEY, "+KEY_FAVORITE_TEXT+" TEXT,"
                + KEY_ACNUM + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public void removeFav(int id) {
        String countQuery = "DELETE FROM " + TABLE_NAME + " where " + KEY_ID + "= " + id ;
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(countQuery);
    }

    public void addFav(String addstring, int activity_num) {
        //다이얼로그에서 추가버튼을 누르면 스트링을 받아와서 넣는다
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FAVORITE_TEXT, addstring);
        values.put(KEY_ACNUM, activity_num);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ItemFavoriteList getFavItem(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String where = "SELECT  * FROM " + TABLE_NAME + " WHERE " + KEY_ID + " = " + id;
        Cursor c = db.rawQuery(where, null);
        ItemFavoriteList item = null;
        try {
            if (c.moveToFirst()){
                item = new ItemFavoriteList();
                item.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                item.setText(c.getString(c.getColumnIndex(KEY_FAVORITE_TEXT)));
                item.setActivity_num(Integer.parseInt(c.getString(c.getColumnIndex(KEY_ACNUM))));
            }
        }
        finally {
            c.close();
        }
        return item;
    }
    public List<ItemFavoriteList> getFavList(){
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        List<ItemFavoriteList> FavList = new ArrayList<ItemFavoriteList>();
        if (cursor.moveToFirst()) {
            do {
                ItemFavoriteList list = new ItemFavoriteList();
                list.setId(Integer.parseInt(cursor.getString(0)));
                list.setText(cursor.getString(1));
                list.setActivity_num(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ACNUM))));
                FavList.add(list);
            } while (cursor.moveToNext());
        }
        return FavList;
    }
}
