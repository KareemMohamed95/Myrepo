package com.example.karee.androidproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class CategoriesActivity extends AppCompatActivity {

    private ImageButton Laptops;
    private ImageButton Mobiles;
    private ImageButton Printers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Laptops = (ImageButton)findViewById(R.id.LapB);
        Mobiles = (ImageButton)findViewById(R.id.MobileB);
        Printers = (ImageButton)findViewById(R.id.PrinterB);

        final Intent intent = new Intent(CategoriesActivity.this,ViewProducts.class);
        intent.putExtra("email",getIntent().getStringExtra("email"));
        Laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("product","Laptops");
                startActivity(intent);
            }
        });
        Mobiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("product","Mobiles");
                startActivity(intent);
            }
        });
        Printers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("product","Printers");
                startActivity(intent);
            }
        });
    }
}
