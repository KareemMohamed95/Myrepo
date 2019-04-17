package com.example.karee.androidproject;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class Test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ListView L = (ListView)findViewById(R.id.testlist);
        ArrayAdapter<String> ee = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        L.setAdapter(ee);
        /*Product RM = new Product(getApplicationContext());
        Cursor cursor = RM.RetRow();
        while(!cursor.isAfterLast())
        {
            ee.add(cursor.getString(0));
            ee.add(cursor.getString(1));
            ee.add(cursor.getString(2));
            ee.add(cursor.getString(3));
            ee.add(cursor.getString(4));
            cursor.moveToNext();
        }*/
        UserCart US = new UserCart(getApplicationContext());
        Cursor cursor = US.RetRow();
        while(!cursor.isAfterLast())
        {
            ee.add(cursor.getString(0));
            ee.add(cursor.getString(1));
            cursor.moveToNext();
        }
    }
}
