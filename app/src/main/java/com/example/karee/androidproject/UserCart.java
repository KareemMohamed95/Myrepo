package com.example.karee.androidproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserCart extends SQLiteOpenHelper {
    private static String DBName = "UserCart";
    SQLiteDatabase UserCartDB;
    public UserCart(Context context)
    {
        super(context,DBName,null,1);
    }
    public void onCreate(SQLiteDatabase DB)
    {
        DB.execSQL("create table usercart(email text primary key,cart text)");
    }
    public void onUpgrade(SQLiteDatabase DB,int oldV,int newV)
    {
        DB.execSQL("drop table if exists usercart");
        onCreate(DB);
    }
    public void addUser(String email)
    {
        ContentValues row = new ContentValues();
        row.put("email",email);
        row.put("cart","");
        UserCartDB = getWritableDatabase();
        UserCartDB.insert("usercart",null,row);
        UserCartDB.close();
    }
    public void UpdateCart(int operation,String email,String Item) {
        UserCartDB = getReadableDatabase();
        Cursor cursor = UserCartDB.rawQuery("select cart from usercart where email like ?", new String[]{email});
        cursor.moveToFirst();
        String OldCart = cursor.getString(0);
        if(operation==1)OldCart = "";
        UserCartDB.close();
        UserCartDB = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("email",email);
        row.put("cart",OldCart+Item);
        UserCartDB.update("usercart",row,"email like ?",new String[]{email});
        UserCartDB.close();
    }
    public Cursor RetUser(String email)
    {
        UserCartDB = getReadableDatabase();
        Cursor cursor = UserCartDB.rawQuery("select * from usercart where email like ?",new String[]{email});
        cursor.moveToFirst();
        UserCartDB.close();
        return cursor;
    }

    //fortesting
    public Cursor RetRow()
    {
        UserCartDB = getReadableDatabase();
        String[] Row = {"email","cart"};
        Cursor cursor = UserCartDB.query("usercart",Row,null,null,null,null,null);
        cursor.moveToFirst();
        UserCartDB.close();
        return cursor;
    }

    public void Del()
    {
        UserCartDB = getWritableDatabase();
        UserCartDB.execSQL("delete from "+"usercart");
        UserCartDB.close();
    }
}
