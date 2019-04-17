package com.example.karee.androidproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RememberMe extends SQLiteOpenHelper {
    private static String DBName = "RememberMe";
    SQLiteDatabase RememberMeDB;
    public RememberMe(Context context)
    {
        super(context,DBName,null,1);
    }
    public void onCreate(SQLiteDatabase DB)
    {
        DB.execSQL("create table remember(rememberid integer,email text,pass text)");
    }
    public void onUpgrade(SQLiteDatabase DB,int oldV,int newV)
    {
        DB.execSQL("drop table if exists remember");
        onCreate(DB);
    }
    public  void AddRow(int id,String email,String pass)
    {
        ContentValues row = new ContentValues();
        row.put("rememberid",id);
        row.put("email",email);
        row.put("pass",pass);
        RememberMeDB = getWritableDatabase();
        RememberMeDB.insert("remember",null,row);
        RememberMeDB.close();
    }
    public  void DeleteRow()
    {
        RememberMeDB = getWritableDatabase();
        RememberMeDB.execSQL("delete from "+"remember");
        RememberMeDB.close();
    }

    //for testing
    public Cursor RetRow()
    {
        RememberMeDB = getReadableDatabase();
        String[] Row = {"rememberid","email","pass"};
        Cursor cursor = RememberMeDB.query("remember",Row,null,null,null,null,null);
        cursor.moveToFirst();
        RememberMeDB.close();
        return cursor;
    }
}
