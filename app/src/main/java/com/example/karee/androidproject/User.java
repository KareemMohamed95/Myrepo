package com.example.karee.androidproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class User extends SQLiteOpenHelper {
    private static String DBName = "OnlineShop";
    SQLiteDatabase OnlineShopDB;
    public User(Context context)
    {
        super(context,DBName,null,1);
    }
    public void onCreate(SQLiteDatabase DB)
    {
        DB.execSQL("create table user(email text primary key,name text not null,password text not null,birthdate text,question text,answer text)");
    }
    public void onUpgrade(SQLiteDatabase DB,int oldV,int newV)
    {
        DB.execSQL("drop table if exists user");
        onCreate(DB);
    }
    public void addUser(String email,String name,String password,String birthdate,String question,String answer)
    {
        ContentValues row = new ContentValues();
        row.put("email",email);
        row.put("name",name);
        row.put("password",password);
        row.put("birthdate",birthdate);
        row.put("question",question);
        row.put("answer",answer);
        OnlineShopDB = getWritableDatabase();
        OnlineShopDB.insert("user",null,row);
        OnlineShopDB.close();
    }
    public Cursor showUser(String email)
    {
        OnlineShopDB = getReadableDatabase();
        String[] userinfo={email};
        Cursor cursor = OnlineShopDB.rawQuery("select * from user where email like ?",userinfo);
        cursor.moveToFirst();
        OnlineShopDB.close();
        return cursor;
    }
    public String CheckUserEmail(String email)
    {
        OnlineShopDB = getReadableDatabase();
        String[] userinfo={"email","name","password","birthdate","question","answer"};
        Cursor cursor = OnlineShopDB.query("user",userinfo,null,null,null,null,null,null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                if(cursor.getString(0).equals(email))
                {
                    OnlineShopDB.close();
                    return cursor.getString(2);
                }
                cursor.moveToNext();
            }
            OnlineShopDB.close();
            return "";
        }
        OnlineShopDB.close();
        return "";
    }
    public  void UpdatePassword(String Email,String NewPassword)
    {
        OnlineShopDB = getReadableDatabase();
        Cursor cursor = OnlineShopDB.rawQuery("select * from user where email like ?",new String[]{Email});
        ContentValues row = new ContentValues();
        cursor.moveToFirst();
        row.put("email",cursor.getString(0));
        row.put("name",cursor.getString(1));
        row.put("password",NewPassword);
        row.put("birthdate",cursor.getString(3));
        row.put("question",cursor.getString(4));
        row.put("answer",cursor.getString(5));
        OnlineShopDB.close();
        OnlineShopDB = getWritableDatabase();
        OnlineShopDB.update("user",row,"email like ?",new String[]{Email});
        OnlineShopDB.close();
    }
    public void Del()
    {
        OnlineShopDB = getWritableDatabase();
        OnlineShopDB.execSQL("delete from "+"user");
        OnlineShopDB.close();
    }
}
