package com.example.karee.androidproject;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class AddProduct extends AppCompatActivity {

    private String ItemName,ItemId;
    private int ItemPrice,ItemQuantity,TotalQuantity,TotalPrice;
    private SeekBar seekBar;
    private Button Submitt;
    private TextView ItemField;
    private TextView QuantityField;
    private TextView PriceField;
    private Product PR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        ItemName = getIntent().getStringExtra("ItemName");
        ItemField = (TextView)findViewById(R.id.PNAME1);
        QuantityField = (TextView)findViewById(R.id.PQ1);
        PriceField = (TextView)findViewById(R.id.TPRICE1);
        seekBar = (SeekBar)findViewById(R.id.PQQ2);
        Submitt = (Button)findViewById(R.id.PSUBMIT1);
        PR = new Product(getApplicationContext());

        ItemField.setText(ItemName);
        Cursor cursor = PR.GetProductINFO(0,"","",getIntent().getStringExtra("category"));
        while(true)
        {
            if((cursor.getString(1)).equals(ItemName))
            {
                ItemId = cursor.getString(0);
                ItemPrice = Integer.parseInt((cursor.getString(2)));
                ItemQuantity = Integer.parseInt((cursor.getString(3)));
                break;
            }
            cursor.moveToNext();
        }
        QuantityField.setText("1");
        PriceField.setText(Integer.toString(ItemPrice));
        seekBar.setProgress(1);
        seekBar.setMax(ItemQuantity);
        TotalQuantity = 1;
        TotalPrice = ItemPrice;
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TotalQuantity = progress;
                TotalPrice = ItemPrice*TotalQuantity;
                QuantityField.setText(Integer.toString(TotalQuantity));
                PriceField.setText(Integer.toString(TotalPrice));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        Submitt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TotalPrice==0)
                {
                    Toast.makeText(getApplicationContext(), "Nothing added", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(AddProduct.this, ViewProducts.class);
                    intent.putExtra("ItemId", ItemId);
                    intent.putExtra("TotalQuantity", Integer.toString(TotalQuantity));
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}
