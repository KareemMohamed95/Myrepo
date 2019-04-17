package com.example.karee.androidproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {


    private CheckBox LaptopCheck,MobileCheck,PrinterCheck;
    private EditText Pricemin,Pricemax,Quantitymin,Quantitymax,Namesearch;
    private Button FilterButton;
    private Product PR;
    private ArrayList<String> ResultList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        PR = new Product(getApplicationContext());
        LaptopCheck = (CheckBox)findViewById(R.id.LaptopCheck);
        MobileCheck = (CheckBox)findViewById(R.id.MobileCheck);
        PrinterCheck = (CheckBox)findViewById(R.id.PrinterCheck);
        Pricemin = (EditText)findViewById(R.id.Pricemin);
        Pricemax = (EditText)findViewById(R.id.Pricemax);
        Quantitymin = (EditText)findViewById(R.id.Quantitymin);
        Quantitymax = (EditText)findViewById(R.id.Quantitymax);
        Namesearch = (EditText)findViewById(R.id.Namesearch);
        FilterButton = (Button)findViewById(R.id.FilterBtn);

        FilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String LaptopC,PrinterC,MobileC;
                if(LaptopCheck.isChecked())LaptopC = "1";
                else LaptopC = "0";
                if(MobileCheck.isChecked())MobileC = "1";
                else MobileC = "0";
                if(PrinterCheck.isChecked())PrinterC = "1";
                else PrinterC = "0";
                String PriceMin = Pricemin.getText().toString();
                String PriceMax = Pricemax.getText().toString();
                String QuantityMin = Quantitymin.getText().toString();
                String QuantityMax = Quantitymax.getText().toString();
                String NameSearch = Namesearch.getText().toString();
                if(PriceMin.equals(""))PriceMin = "0";
                if(PriceMax.equals(""))PriceMax = "15000";
                if(QuantityMin.equals(""))QuantityMin = "0";
                if(QuantityMax.equals(""))QuantityMax = "9";
                int PMin = Integer.parseInt(PriceMin),PMax = Integer.parseInt(PriceMax),QMin = Integer.parseInt(QuantityMin),QMax = Integer.parseInt(QuantityMax);
                if(PMax<PMin||QMax<QMin)
                {
                    Toast.makeText(getApplicationContext(),"Error in parameters",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent intent = new Intent(SearchActivity.this,SearchActivity2.class);
                    intent.putExtra("email",getIntent().getStringExtra("email"));
                    intent.putExtra("NameSearch",NameSearch);
                    intent.putExtra("LaptopC",LaptopC);
                    intent.putExtra("MobileC",MobileC);
                    intent.putExtra("PrinterC",PrinterC);
                    intent.putExtra("PriceMin",PriceMin);
                    intent.putExtra("PriceMax",PriceMax);
                    intent.putExtra("QuantityMin",QuantityMin);
                    intent.putExtra("QuantityMax",QuantityMax);
                    startActivity(intent);
                }
            }
        });
    }
}
