package com.example.karee.androidproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Product extends SQLiteOpenHelper {
    private static String DBName = "Product";
    SQLiteDatabase ProductDB;
    public Product(Context context)
    {
        super(context,DBName,null,1);
    }
    public void onCreate(SQLiteDatabase DB)
    {
        DB.execSQL("create table product(id text primary key,name text,category text,price integer,quantity integer)");
    }
    public void onUpgrade(SQLiteDatabase DB,int oldV,int newV)
    {
        DB.execSQL("drop table if exists product");
        onCreate(DB);
    }
    public void AddProduct(String id,String name,String category,int price,int quantity)
    {
        ContentValues row = new ContentValues();
        row.put("id",id);
        row.put("name",name);
        row.put("category",category);
        row.put("price",price);
        row.put("quantity",quantity);
        ProductDB = getWritableDatabase();
        ProductDB.insert("product",null,row);
        ProductDB.close();
    }
    public void Del()
    {
        ProductDB = getWritableDatabase();
        ProductDB.execSQL("delete from "+"product");
        ProductDB.close();
    }
    public void UpdateProduct(String id,int DeletedQuantity)
    {
        ProductDB = getReadableDatabase();
        Cursor cursor = ProductDB.rawQuery("select * from product where id like ?",new String[]{id});
        ContentValues row = new ContentValues();
        cursor.moveToFirst();
        String ID = cursor.getString(0);
        row.put("id",cursor.getString(0));
        row.put("name",cursor.getString(1));
        row.put("category",cursor.getString(2));
        row.put("price",Integer.parseInt(cursor.getString(3)));
        row.put("quantity",Integer.parseInt(cursor.getString(4))-DeletedQuantity);
        ProductDB.close();
        ProductDB = getWritableDatabase();
        ProductDB.update("product",row,"id like ?",new String[]{ID});
        ProductDB.close();
    }
    public Cursor GetProductINFO(int operation,String PID,String Name,String Category)
    {
        ProductDB = getReadableDatabase();
        Cursor cursor;
        if(operation==2)cursor = ProductDB.rawQuery("select id,quantity,category,price from product where name like ?",new String[]{Name});
        else if(operation==1){cursor = ProductDB.rawQuery("select name,price from product where id like ?",new String[]{PID});}
        else {cursor = ProductDB.rawQuery("select id,name,price,quantity from product where category like ?",new String[]{Category});}
        cursor.moveToFirst();
        ProductDB.close();
        return cursor;
    }
    public ArrayList<String> SearchProducts(String NameSearch,String[] CategorySearch,int[] Details)
    {
        ArrayList<String> SearchResult = new ArrayList<String>();
        ProductDB = getReadableDatabase();
        String[] Row = {"name","category","price","quantity"};
        Cursor cursor = ProductDB.query("product",Row,null,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            int Found = 0;
            String Name = cursor.getString(0);
            String Category = cursor.getString(1);
            int Price = Integer.parseInt(cursor.getString(2));
            int Quantity = Integer.parseInt(cursor.getString(3));
            if(NameSearch.equals(""))Found = 1;
            if(Found==0)
            {
                String Name2 = Name.toLowerCase();
                String NameSearch2 = NameSearch.toLowerCase();
                if(Name2.length()>NameSearch2.length())
                {
                    for(int i = 0;i <= Name2.length()-NameSearch2.length();i++)
                    {
                        if((Name2.substring(i,i+NameSearch2.length())).equals(NameSearch2))
                        {
                            Found = 1;
                            break;
                        }
                    }
                }
            }
            if(Found==0)
            {
                cursor.moveToNext();
                continue;
            }
            Found = 0;
            if(Category.equals("Mobiles")&&CategorySearch[0].equals("1"))Found = 1;
            if(Category.equals("Laptops")&&CategorySearch[1].equals("1"))Found = 1;
            if(Category.equals("Printers")&&CategorySearch[2].equals("1"))Found = 1;
            if(Found==0)
            {
                cursor.moveToNext();
                continue;
            }
            if(Price<Details[0]||Price>Details[1]||Quantity<Details[2]||Quantity>Details[3])Found = 0;
            if(Found==0)
            {
                cursor.moveToNext();
                continue;
            }
            SearchResult.add(Name);
            cursor.moveToNext();
        }
        ProductDB.close();
        return SearchResult;
    }
    //for testing
    public Cursor RetRow()
    {
        ProductDB = getReadableDatabase();
        String[] Row = {"id","name","category","price","quantity"};
        Cursor cursor = ProductDB.query("product",Row,null,null,null,null,null);
        cursor.moveToFirst();
        ProductDB.close();
        return cursor;
    }
}
