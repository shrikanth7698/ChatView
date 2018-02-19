package com.shrikanthravi.chatviewlibrary;

/**
 * Created by shrikanthravi on 16/02/18.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class DBHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ChatUI";
    public static final String TABLE_NAME1 = "ChatDB";


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME1 + " (MID INTEGER PRIMARY KEY AUTOINCREMENT, TYPE TEXT, DATE_TIME TEXT, MESSAGE TEXT,IMAGES TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        onCreate(db);


    }



    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME1);
        return numRows;
    }


    public ArrayList<Message> getAllMessages(int start,int include){
        ArrayList<Message> chat = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME1+" order by MID desc LIMIT "+start+", "+include, null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            String[] images = res.getString(res.getColumnIndex("IMAGES")).split("#");
            List<Uri> imagesList = new ArrayList<>();
            for(int i=0;i<images.length;i++){
                imagesList.add(Uri.parse(images[i]));
            }
            chat.add(new Message(Long.valueOf(res.getInt(res.getColumnIndex("MID"))),res.getString(res.getColumnIndex("TYPE")),res.getString(res.getColumnIndex("MESSAGE")),res.getString(res.getColumnIndex("DATE_TIME")),imagesList));
            res.moveToNext();

        }
        return chat;
    }

    public ArrayList<Message> getAllMessages(){
        ArrayList<Message> chat = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME1, null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            String[] images = res.getString(res.getColumnIndex("IMAGES")).split("#");
            List<Uri> imagesList = new ArrayList<>();
            for(int i=0;i<images.length;i++){
                imagesList.add(Uri.parse(images[i]));
            }
            chat.add(new Message(Long.valueOf(res.getInt(res.getColumnIndex("MID"))),res.getString(res.getColumnIndex("TYPE")),res.getString(res.getColumnIndex("DATE_TIME")),res.getString(res.getColumnIndex("MESSAGE")),imagesList));
            res.moveToNext();

        }
        return chat;
    }

    public Boolean insertMessage(Message message){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("DATE_TIME", message.getTime());
        contentValues.put("TYPE", message.getType());
        contentValues.put("MESSAGE", message.getBody());
        String images = "";
        if(message.getImageList()!=null) {
            for (int i = 0; i < message.getImageList().size(); i++) {
                images = images + message.getImageList().get(i).toString() + "#";
            }
            contentValues.put("IMAGES", images);
        }
        else{
            contentValues.put("IMAGES", "");
        }
        System.out.println("Testing1 "+images);
        db.insert(TABLE_NAME1, null, contentValues);
        System.out.println("inserted");
        return true;
    }
}


